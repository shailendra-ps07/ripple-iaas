# ripple-iaas

Steps to Run:
  1. mvn clean install
  2. Configure Application to run with parameter (server , iaas-service/config/iaas-config.yaml

Notes:
  1. iaas_2019-07-30.sql.gz contains tables with metadata for testing
  2. Application is configured to connect to remote host on AWS RDS so can be run directly
  3. VM's are preconfigured to accept following configuration:
    RAM: ram1(8gb), ram2(4gb), ram3(2gb), ram4(16gb).
    Hard Disk: hd1(128gb), hd2(256gb), hd3(512gb), hd4(1024gb)
    CPU Cores: cc1(2), cc2(4), cc3(8), cc4(16) 
    Operating Systems: Linux, macOS, windows
    
    These are the only valid values for configuring Virtaul Memory. So please use these values in API.
    More values can be added in DB if needed.
 
  4. Postman Link: https://documenter.getpostman.com/view/117682/SVYkxMYy?version=latest
  5. Added postman collection for reference at root
  6. Every request except Register User need to have Bearer <Token> header so make sure to add it after registering new user.
  

