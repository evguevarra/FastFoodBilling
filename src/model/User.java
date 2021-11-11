package model;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String gender;
    private String birthdate;
    private String contactNumber;
    private String position;
    private String imageSrc;

    public User(int id,String firstname,String lastname,String gender,String birthdate, String contactNumber, String position,String imageSrc){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdate =birthdate;
        this.contactNumber = contactNumber;
        this.position =position;
        this.imageSrc = imageSrc;
    }

    public User(int id,String firstname,String lastname,String gender,String birthdate, String contactNumber, String position){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdate =birthdate;
        this.contactNumber = contactNumber;
        this.position =position;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
