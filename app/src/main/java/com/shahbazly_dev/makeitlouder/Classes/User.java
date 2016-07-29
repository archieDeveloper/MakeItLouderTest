package com.shahbazly_dev.makeitlouder.Classes;


import com.vk.sdk.api.model.VKApiUserFull;

/***
 * Сущность пользователя
 */
public class User {

    private String firstName, lastName, avatar;

    /***
     * Создание пользователя при помощи vk
     * @param user объект пользователя vk
     */
    public User(VKApiUserFull user) {
        firstName = user.first_name;
        lastName = user.last_name;
        avatar = user.photo_100;
    }

    /***
     * Получиь имя
     * @return имя
     */
    public String getFirstName() {
        return firstName;
    }

    /***
     * Получить фамилию
     * @return фамилия
     */
    public String getLastName() {
        return lastName;
    }

    /***
     * Получить полное имя
     * @return полное имя
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /***
     * Получить аватар
     * @return аватар
     */
    public String getAvatar() {
        return avatar;
    }

}
