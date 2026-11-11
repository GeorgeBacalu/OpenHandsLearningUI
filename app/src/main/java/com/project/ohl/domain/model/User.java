package com.project.ohl.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class User {

   private UUID id;

   private String name;

   private String email;

   private String password;

   private String mobile;

   private String address;

   private LocalDate birthday;

   private UUID schoolId;

   private UUID roleId;

   public User() {
   }

   public User(UUID id, String name, String email, String password, String mobile, String address, LocalDate birthday, UUID schoolId, UUID roleId) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.mobile = mobile;
      this.address = address;
      this.birthday = birthday;
      this.schoolId = schoolId;
      this.roleId = roleId;
   }

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public LocalDate getBirthday() {
      return birthday;
   }

   public void setBirthday(LocalDate birthday) {
      this.birthday = birthday;
   }

   public UUID getSchoolId() {
      return schoolId;
   }

   public void setSchoolId(UUID schoolId) {
      this.schoolId = schoolId;
   }

   public UUID getRoleId() {
      return roleId;
   }

   public void setRoleId(UUID roleId) {
      this.roleId = roleId;
   }

   @Override
   public String toString() {
      return "UserDto{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", email='" + email + '\'' +
           ", mobile='" + mobile + '\'' +
           ", address='" + address + '\'' +
           ", birthday=" + birthday +
           ", schoolId=" + schoolId +
           ", roleId=" + roleId +
           '}';
   }

}
