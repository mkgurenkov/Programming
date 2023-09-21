package commands;
/**
 * @author Maksim Gurenkov
 * @version 1.0
 * Class that embodies "help" command
 */
public class HelpCommand {
    /** Field which keeps command output*/
    private String output;
    /** Constructor*/
    public HelpCommand() {
        setOutput();
    }
    /** Execute method: prints brief information about all commands*/
    public void execute() {
        System.out.println(output);
    }
    /** Method, which sets output of Help command into the appropriate field*/
    private void setOutput(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("help - вывести справку по доступным командам").append("\n");
        stringBuilder.append("info - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)").append("\n");
        stringBuilder.append("show - вывести в стандартный поток вывода все элементы коллекции в строковом представлении").append("\n");
        stringBuilder.append("insert key (Integer) {element} - добавить новый элемент с заданным ключом").append("\n");
        stringBuilder.append("update id (Integer) {element} - обновить значение элемента коллекции, id которого равен заданному").append("\n");
        stringBuilder.append("remove_key key (Integer) - удалить элемент из коллекции по его ключу").append("\n");
        stringBuilder.append("clear - очистить коллекцию").append("\n");
        stringBuilder.append("execute_script file_name - считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.").append("\n");
        stringBuilder.append("exit - завершить программу (без сохранения в файл)").append("\n");
        stringBuilder.append("remove_lower {element} - удалить из коллекции все элементы, меньшие, чем заданный").append("\n");
        stringBuilder.append("replace_if_greater key (Integer) {element} - заменить значение по ключу, если новое значение больше старого").append("\n");
        stringBuilder.append("remove_greater_key key (Integer) - удалить из коллекции все элементы, ключ которых превышает заданный").append("\n");
        stringBuilder.append("remove_any_by_government government (\"KRITARCHY\", \"PUPPET_STATE\", \"JUNTA\") - удалить из коллекции один элемент, значение поля government которого эквивалентно заданному").append("\n");
        stringBuilder.append("filter_by_climate climate (\"MONSOON\", \"HUMIDCONTINENTAL\", \"MEDITERRANIAN\", \"TUNDRA\") - вывести элементы, значение поля climate которых равно заданному").append("\n");
        stringBuilder.append("print_field_descending_climate - вывести значения поля climate всех элементов в порядке убывания");
        output = stringBuilder.toString();
    }
    /** Method, which gets output of Help command*/
    public String getOutput() {
        return output;
    }
}
