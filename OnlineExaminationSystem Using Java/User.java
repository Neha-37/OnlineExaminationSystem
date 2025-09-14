public class User {
    private String username;
    private String password;
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }
    public String getUsername(){
        return username;
    }
    public void updatePassword(String newPassword){
        this.password=newPassword;
    }
    public boolean verifyPassword(String password){
        return this.password.equals(password);
    }
}
