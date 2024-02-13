package menu;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private String prompt;
    final private List<String> options;
    final private List<String> secretOptions;
    final private List<String> selectors;
    final private HashMap<String, String> acceptedInput;

    public Menu () {
        this.prompt = "";
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

        while (true) {
            System.out.println(this.prompt);
            System.out.println();
            for (int i = 0; i < this.options.size(); i++){
                System.out.println(this.selectors.get(i) + ") " + this.options.get(i));
            }

            System.out.println();
            System.out.print("> ");

            String response = scanner.nextLine().trim();
            if (this.acceptedInput.containsKey(response)) {
                return this.acceptedInput.get(response);
            }
        }
    }
}
