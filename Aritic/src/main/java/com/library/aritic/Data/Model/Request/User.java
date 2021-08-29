package com.library.aritic.Data.Model.Request;



public class User {

        private String email;
        private String userId;
        private String deviceId;
        private String phone;

        public User(String email, String userId, String deviceId, String phone) {
                this.email = email;
                this.userId = userId;
                this.deviceId = deviceId;
                this.phone = phone;
        }

        public String getEmail() {
                return email;
        }

        public String getUserId() {
                return userId;
        }

        public String getDeviceId() {
                return deviceId;
        }

        public String getPhone() {
                return phone;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }
}
