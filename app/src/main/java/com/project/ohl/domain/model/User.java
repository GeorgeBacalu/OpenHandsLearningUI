package com.project.ohl.domain.model;

import java.util.UUID;

public class User {

   private UUID id;

   private String name;

   private String email;

   private String password;

   private String phone;

   private UUID schoolId;

   private UUID roleId;

   public User() {
   }

   public User(UUID id, String name, String email, String password, String phone, UUID schoolId, UUID roleId) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.phone = phone;
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

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
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
      return "User{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", email='" + email + '\'' +
           ", phone='" + phone + '\'' +
           ", schoolId=" + schoolId +
           ", roleId=" + roleId +
           '}';
   }

}
