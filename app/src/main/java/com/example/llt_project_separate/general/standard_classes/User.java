package com.example.llt_project_separate.general.standard_classes;

import java.time.LocalDate;

public class User {

   private Long id;

   private String name;

   private String email;

   private String password;

   private String mobile;

   private String address;

   private LocalDate birthday;

   private Long institutionId;

   private Long roleId;

   public User() {
   }

   public User(Long id, String name, String email, String password, String mobile, String address, LocalDate birthday, Long institutionId, Long roleId) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.password = password;
      this.mobile = mobile;
      this.address = address;
      this.birthday = birthday;
      this.institutionId = institutionId;
      this.roleId = roleId;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
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

   public Long getInstitutionId() {
      return institutionId;
   }

   public void setInstitutionId(Long institutionId) {
      this.institutionId = institutionId;
   }

   public Long getRoleId() {
      return roleId;
   }

   public void setRoleId(Long roleId) {
      this.roleId = roleId;
   }

   @Override
   public String toString() {
      return "UserDto{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", email='" + email + '\'' +
           ", password='" + password + '\'' +
           ", mobile='" + mobile + '\'' +
           ", address='" + address + '\'' +
           ", birthday=" + birthday +
           ", institutionId=" + institutionId +
           ", roleId=" + roleId +
           '}';
   }
}
