package br.art.jss.todolist.user;

public class UserModel {

    private String username;
    private String name;
    private String password;

    /**
     * Retorna o username
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Define o username
     * @param username
     * @return String
     */
    public String setUsername(String username) {
        this.username = username;

        return this.username;
    }

    /**
     * Retorna o nome
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Define o nome
     * @param name
     * @return String
     */
    public String setName(String name) {
        this.name = name;

        return this.name;
    }

    /**
     * Retorna a senha
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Define a senha
     * @param password
     * @return String
     */
    public String setPassword(String password) {
        this.password = password;

        return this.password;
    }
}
