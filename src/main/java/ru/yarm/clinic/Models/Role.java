package ru.yarm.clinic.Models;

public enum Role {
    PATIENT("Пациент"), EMPLOYEE("Сотрудник"), DOCTOR("Врач"), MANAGER("Менеджер"), ADMIN("Администратор"), BANNED("Заблокированный"), OWNER("Владелец");

    private String title;
    private Role(String title){
        this.title=title;
    }

    public String getTitle(){
        return this.title;
    }


}
