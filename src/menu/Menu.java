package menu;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private String prompt;
    private String borderChar;
    private String invalidFeedback;

    final private int width = 32;
    final private List<String> options;
    final private List<String> secretOptions;
    final private List<String> selectors;
    final private HashMap<String, String> acceptedInput;

    public Menu () {
        this.prompt = "";
        this.invalidFeedback = "";
        this.borderChar = "";
        this.options = new ArrayList<>();
        this.secretOptions = new ArrayList<>();
        this.selectors = new ArrayList<>();
        this.acceptedInput = new HashMap<>();
    }

    public List<String> addOption(String newOption, String selector, List<String> alts) {
        this.options.add(newOption);
        this.selectors.add(selector);

        this.acceptedInput.put(newOption, newOption);
        this.acceptedInput.put(selector, newOption);

        for(String alt : alts) {
            acceptedInput.put(alt, newOption);
        }

        return this.options;
    }

    public List<String> addOption(String newOption, String selector) {
        return addOption(newOption, selector, List.of());
    }

    public List<String> addSecretOption(String newOption, List<String> alts) {
        this.secretOptions.add(newOption);
        this.acceptedInput.put(newOption, newOption);
        for(String alt : alts) {
            acceptedInput.put(alt, newOption);
        }

        return this.secretOptions;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setInvalidFeedback(String invalidFeedback) {
        this.invalidFeedback = invalidFeedback;
    }

    public void setBorderChar(String borderChar) {
        this.borderChar = borderChar;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<String> getOptionAlts(String option) {
        return this.acceptedInput
                .keySet()
                .stream()
                .filter(key -> this.acceptedInput.get(key).equals(option))
                .collect(Collectors.toList());
    }

    public String ask () {
        Scanner scanner = new Scanner(System.in);
        printBorder();

        System.out.println(this.prompt);
        System.out.println();
        for (int i = 0; i < this.options.size(); i++){
            System.out.println(this.selectors.get(i) + ") " + this.options.get(i));
        }
        System.out.println();
        while (true) {
            System.out.print("> ");

            String response = scanner.nextLine().trim();
            if (this.acceptedInput.containsKey(response)) {
                response = this.acceptedInput.get(response);
                String formatted = !this.borderChar.equals("")
                        ?"%" + this.width + "s\n"
                        :"%s\n";

                System.out.printf(formatted, "SELECTED: " + response);
                printBorder();
                return this.acceptedInput.get(response);
            }

            if (!this.invalidFeedback.equals("")) {
                System.out.println(this.invalidFeedback);
            }
        }
    }

    private void printBorder() {
        if (!this.borderChar.equals("")) {
            System.out.println(String.format(this.borderChar).repeat(this.width));
        }
    }
}
