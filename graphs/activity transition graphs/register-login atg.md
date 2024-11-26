graph TD
    Start([Start]) --> RegistrationForm[User Fills Registration Form]
    RegistrationForm --> ValidateRegistration[Validate Registration Data]
    ValidateRegistration -- Invalid Data --> ErrorRegistration[Show Error Message]
    ValidateRegistration -- Valid Data --> CheckUserExist[Check If User Already Exists]
    CheckUserExist -- User Exists --> ErrorRegistrationExist[Show 'User Already Exists' Error]
    CheckUserExist -- User Does Not Exist --> EncryptPassword[Encrypt Password]
    EncryptPassword --> AssignDefaultRole[Assign Default Role: USER]
    AssignDefaultRole --> SaveUser[Save User to Database]
    SaveUser --> RegistrationSuccess[Return Registration Success]

    Start --> LoginForm[User Fills Login Form]
    LoginForm --> ValidateLogin[Validate Login Data]
    ValidateLogin -- Invalid Data --> ErrorLogin[Show Error Message]
    ValidateLogin -- Valid Data --> Authenticate[Authenticate Credentials]
    Authenticate -- Disabled Account --> ErrorDisabled[Show 'Account Disabled' Error]
    Authenticate -- Invalid Credentials --> ErrorInvalid[Show 'Invalid Credentials' Error]
    Authenticate -- Valid Credentials --> GenerateToken[Generate JWT Token]
    GenerateToken --> LoginSuccess[Return Login Success]

    classDef startEnd fill:#4CAF50,stroke:#fff,stroke-width:2px;
    class Start,RegistrationSuccess,LoginSuccess startEnd;
    
    classDef process fill:#FFC107,stroke:#fff,stroke-width:2px;
    class RegistrationForm,ValidateRegistration,CheckUserExist,EncryptPassword,AssignDefaultRole,SaveUser,LoginForm,ValidateLogin,Authenticate,GenerateToken process;

    classDef decision fill:#FF5722,stroke:#fff,stroke-width:2px;
    class ValidateRegistration,CheckUserExist,ValidateLogin,Authenticate decision;
    
    classDef error fill:#F44336,stroke:#fff,stroke-width:2px;
    class ErrorRegistration,ErrorRegistrationExist,ErrorLogin,ErrorDisabled,ErrorInvalid error;
