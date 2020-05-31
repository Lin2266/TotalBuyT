package uuu.totalbuy.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer {

    public static final char MALE = 'M';
    public static final char FEMALE = 'F';

    private String id;
    private String name;
    private String password;
    private char gender = MALE;//M:男, F:女
    private String email;

    private Date birthDate;
    private String address;
    private String phone;
    private boolean married;
    private BloodType bloodType; //0:O型, 1:A型, 2:B型, 3:AB型

    private int status; //0:新會員, 1: 已確認, >1000:待確認, -1:停用

    public Customer() {

    }

    public Customer(String id, String name, String password, String email) {
        this(id, name, password);
        this.email = email;
    }

    public Customer(String id, String name, String password) {
        this(id, name);
        this.password = password;
    }

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null) {
            if (obj instanceof Customer) {
                Customer c = (Customer) obj;

                //if ( getId().equals( c.getId()) ) {
                if (id.equals(c.id) && email.equals(c.email)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws TotalBuyException {
        if (id != null && (id = id.trim()).length() == 10 && checkId(id)) {
            this.id = id;
        } else {
            throw new TotalBuyException("id欄位必須輸入且格式正確");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TotalBuyException {
        if (name != null && ((name = name.trim()).length() > 0)) {
            this.name = name;
        } else {
            throw new TotalBuyException("姓名欄位必須輸入");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) throws TotalBuyException {
        if (birthDate == null || birthDate.before(new Date())) {
            this.birthDate = birthDate;
        } else {
            throw new TotalBuyException("出生日期必須小於今天");
        }
    }

    public void setBirthDate(String sDate) throws TotalBuyException {
        if (sDate != null && (sDate = sDate.trim()).length() > 0) {
            sDate = sDate.replace('-', '/');
            try {
                this.setBirthDate(DateFormat.getDateInstance().parse(sDate));//yyyy/M/d
            } catch (ParseException ex) {
                throw new TotalBuyException("出生日期資料格式不正確");
            }
        } else {
            this.birthDate = null;
        }
    }

    public String getBirthDateString() {
        if (this.birthDate != null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(this.birthDate);
        } else {
            return "";
        }
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) throws TotalBuyException {
        if (gender == MALE || gender == FEMALE) {
            this.gender = gender;
        } else {
            throw new TotalBuyException("性別資料不正確");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws TotalBuyException {
        if (email != null && (email = email.trim()).matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            this.email = email;
        } else {
            throw new TotalBuyException("email欄位必須輸入且符合email格式");
        }
    }

    private static final String idPattern = "^[A-Z][12][0-9]{8}$";

    public static boolean checkId(String id) {
        boolean answer = false;
        if (id == null || id.length() != 10) {
            return answer;
        } else {
            if (id.matches(idPattern)) {
                char last_char = id.charAt(9);
                if (last_char == getLastNumberFromId(id.substring(0, 9))) {
                    answer = true;
                }
            }
        }
        return answer;
    }

    public static char getLastNumberFromId(String id_9) {
        char first_char = id_9.charAt(0);
        char last_char = '\u0000';

        int s = 0;
        if (first_char >= 'A' && first_char <= 'H') {
            s = (first_char - 'A' + 10);
        } else if (first_char >= 'J' && first_char <= 'N') {
            s = (first_char - 'J' + 18);
        } else if (first_char >= 'P' && first_char <= 'V') {
            s = (first_char - 'P' + 23);
        } else {
            switch (first_char) {
                case 'I':
                    s = 34;
                    break;
                case 'O':
                    s = 35;
                    break;
                case 'W':
                    s = 32;
                    break;
                case 'X':
                    s = 30;
                    break;
                case 'Y':
                    s = 31;
                    break;
                case 'Z':
                    s = 33;
                    break;
                default:
                    System.out.println("不可能");
            }
        }
        int data = (s / 10) * 1 + (s % 10) * 9;
        for (int i = 1; i < 9; i++) {
            int c = Integer.parseInt(id_9.charAt(i) + "") * (8 - i + 1);
            data += c;
        }
        last_char = (char) ((10 - (data % 10)) % 10 + '0');
        assert (last_char >= '0' && last_char <= '9') : "id尾碼不正確: " + last_char;
        return last_char;
    }

    @Override
    public String toString() {
        return String.format("ID:        %s%n"
                + "Name:      %s%n"
                + "Address:   %s%n"
                + "BirthDate: %s%n"
                + "Gender:    %s%n"
                + "Married:   %b%n"
                + "EMail:     %s%n",
                getId(), getName(), getAddress(), getBirthDate(),
                getGender(), isMarried(), getEmail());
        /*
         return "ID:        " + getId() + "\n" +
         "Name:      " + getName() + "\n" +
         "Address:   " + getAddress() + "\n" +
         "BirthDate: " + getBirthDate() + "\n" +
         "Gender:    " + getGender() + "\n" +
         "Married:   " + isMarried() + "\n" +
         "EMail:     " + getEmail();
         */
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws TotalBuyException {
        if (password != null && ((password = password.trim()).length() > 0)) {
            this.password = password;
        } else {
            throw new TotalBuyException("密碼欄位必須輸入");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
