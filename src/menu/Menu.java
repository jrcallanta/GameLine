package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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
    final private HashMap<String, String> optionDescriptors;

    public Menu () {
        this.prompt = "";
        this.invalidFeedback = "";
        this.borderChar = "";
        this.options = new ArrayList<>();
        this.secretOptions = new ArrayList<>();
        this.selectors = new ArrayList<>();
        this.acceptedInput = new HashMap<>();
        this.optionDescriptors = new HashMap<>();
    }
    public List<String> addOption(String newOption, String selector, String descriptor, List<String> alts) {
        this.options.add(newOption);
        this.selectors.add(selector);

        if (descriptor != null) this.optionDescriptors.put(newOption, descriptor);

        this.acceptedInput.put(newOption, newOption);
        this.acceptedInput.put(selector, newOption);
        for (String alt : alts) {
            acceptedInput.put(alt, newOption);
        }

        return this.options;
    }

    public List<String> addOption(String newOption, String selector, List<String> alts) {
        return addOption(newOption, selector, null, alts);
    }

    public List<String> addOption(String newOption, String selector, String descriptor) {
        return addOption(newOption, selector, descriptor, List.of());
    }
    public List<String> addOption(String newOption, String selector) {
        return addOption(newOption, selector, null, List.of());
    }


    public List<String> addSecretOption(String newOption, List<String> alts) {
        this.secretOptions.add(newOption);
        this.acceptedInput.put(newOption, newOption);
        for(String alt : alts) {
            acceptedInput.put(alt, newOption);
        }

        return this.secretOptions;
    }

    public void addDescriptor (String option, String descriptor) {
        this.optionDescriptors.put(option, descriptor);
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

    public String ask (Scanner scanner) {
        printBorder();

        System.out.println(this.prompt);
        System.out.println();
        int longest = this.options
                .stream()
                .mapToInt(o -> o.length())
                .max().orElse(10);

        for (int i = 0; i < this.options.size(); i++){
            String line = String.format(
                    "%s) %-" + longest + "s ",
                    this.selectors.get(i),
                    this.options.get(i));

            if (this.optionDescriptors.containsKey(this.options.get(i))) {
                line += String.format(
                        "%" + (this.width - line.length()) + "s",
                        this.optionDescriptors.get(this.options.get(i))
                );
            }
            System.out.println(line);
        }
        System.out.println();
        while (true) {
            System.out.print("> ");

            String response = scanner.nextLine().trim();
            if (this.acceptedInput.containsKey(response)) {
                response = this.acceptedInput.get(response);
                String formatted = !this.borderChar.isEmpty()
                        ?"%" + this.width + "s\n"
                        :"%s\n";

                System.out.printf(formatted, "SELECTED: " + response);
                printBorder();

                return this.acceptedInput.get(response);
            }

            if (!this.invalidFeedback.isEmpty()) {
                System.out.println(this.invalidFeedback);
            }
        }
    }

    private void printBorder() {
        if (!this.borderChar.isEmpty()) {
            System.out.println(String.format(this.borderChar).repeat(this.width));
        }
    }
}
