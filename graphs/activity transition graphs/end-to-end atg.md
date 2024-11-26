graph TD
    %% Registration Process
    Start([Start]) --> RegistrationForm[User Fills Registration Form]
    RegistrationForm --> ValidateRegistration[Validate Registration Data]
    ValidateRegistration -- Invalid Data --> ErrorRegistration[Show Error Message]
    ValidateRegistration -- Valid Data --> CheckUserExist[Check If User Already Exists]
    CheckUserExist -- User Exists --> ErrorRegistrationExist[Show 'User Already Exists' Error]
    CheckUserExist -- User Does Not Exist --> EncryptPassword[Encrypt Password]
    EncryptPassword --> AssignDefaultRole[Assign Default Role: USER]
    AssignDefaultRole --> SaveUser[Save User to Database]
    SaveUser --> RegistrationSuccess[Return Registration Success]

    %% Login Process
    Start --> LoginForm[User Fills Login Form]
    LoginForm --> ValidateLogin[Validate Login Data]
    ValidateLogin -- Invalid Data --> ErrorLogin[Show Error Message]
    ValidateLogin -- Valid Data --> Authenticate[Authenticate Credentials]
    Authenticate -- Disabled Account --> ErrorDisabled[Show 'Account Disabled' Error]
    Authenticate -- Invalid Credentials --> ErrorInvalid[Show 'Invalid Credentials' Error]
    Authenticate -- Valid Credentials --> GenerateToken[Generate JWT Token]
    GenerateToken --> LoginSuccess[Return Login Success]

    %% Quiz Result Submission Process
    Start --> SubmitQuizForm[User Fills Quiz Answers]
    SubmitQuizForm --> ValidateQuiz[Validate Quiz Data]
    ValidateQuiz -- Invalid Data --> ErrorQuiz[Show Error Message]
    ValidateQuiz -- Valid Data --> CalculateMarks[Calculate Total Marks and Correct Answers]
    CalculateMarks --> SaveQuizResult[Save Quiz Result to Database]
    SaveQuizResult --> QuizResultSuccess[Return Quiz Result Success]

    %% Question Management Process
    Start --> AddQuestion[Add Question]
    AddQuestion -->|Valid Input| SaveQuestion[Save Question to Database]
    AddQuestion -->|Invalid Input| ErrorQuestion[Return Validation Error]
    SaveQuestion --> ViewAllQuestions[View All Questions]
    ViewAllQuestions --> SelectQuestion[Select a Question]
    SelectQuestion -->|View Details| ViewQuestionDetails[View Question Details]
    SelectQuestion -->|Update Question| EditQuestion[Edit Question]
    EditQuestion -->|Valid Input| UpdateQuestion[Update Question in Database]
    EditQuestion -->|Invalid Input| ErrorEditQuestion[Return Validation Error]
    SelectQuestion -->|Delete Question| RemoveQuestion[Remove Question from Database]
    SelectQuestion -->|Get Questions by Quiz| RetrieveQuizQuestions[Retrieve Quiz Questions]
    ViewQuestionDetails --> ViewAssociatedQuiz[View Associated Quiz]
    UpdateQuestion --> ViewAllQuestions
    RemoveQuestion --> ViewAllQuestions
    ErrorQuestion --> Start
    ErrorEditQuestion --> Start

    %% Category Management Process
    Start --> CreateCategory[Create Category]
    CreateCategory -->|Valid Input| SaveCategory[Save Category to Database]
    CreateCategory -->|Invalid Input| ErrorCategory[Return Validation Error]
    SaveCategory --> ViewAllCategories[View All Categories]
    ViewAllCategories --> SelectCategory[Select a Category]
    SelectCategory -->|View Details| ViewCategoryDetails[View Category Details]
    SelectCategory -->|Update Category| EditCategory[Edit Category]
    EditCategory -->|Valid Input| UpdateCategory[Update Category in Database]
    EditCategory -->|Invalid Input| ErrorEditCategory[Return Validation Error]
    SelectCategory -->|Delete Category| RemoveCategory[Remove Category from Database]
    RemoveCategory -->|Cascade Delete Quizzes| DeleteAssociatedQuizzes[Delete Associated Quizzes]
    ViewCategoryDetails --> ViewAssociatedQuizzes
    UpdateCategory --> ViewAllCategories
    RemoveCategory --> ViewAllCategories
    ErrorCategory --> Start
    ErrorEditCategory --> Start

    %% Quiz Management Process
    Start --> CreateQuiz[Create Quiz]
    CreateQuiz -->|Valid Input| SaveQuiz[Save Quiz to Database]
    CreateQuiz -->|Invalid Input| ErrorQuiz[Return Validation Error]
    SaveQuiz --> ViewQuizList[View Quiz List]
    ViewQuizList --> SelectQuiz[Select a Quiz]
    SelectQuiz -->|View Details| ViewQuizDetails[View Quiz Details]
    SelectQuiz -->|Update Quiz| EditQuiz[Edit Quiz]
    EditQuiz -->|Valid Input| UpdateQuiz[Update Quiz in Database]
    EditQuiz -->|Invalid Input| ErrorEditQuiz[Return Validation Error]
    SelectQuiz -->|Delete Quiz| RemoveQuiz[Remove Quiz from Database]
    SelectQuiz -->|View Associated Questions| ViewQuizQuestions[View Associated Questions]
    SelectQuiz -->|View Results| ViewQuizResults[View Quiz Results]
    ViewQuizResults --> AnalyzeQuizPerformance[Analyze Quiz Performance]
    RemoveQuiz --> ViewQuizList
    UpdateQuiz --> ViewQuizList
    ViewQuizQuestions --> ViewQuizList
    AnalyzeQuizPerformance --> ViewQuizList
    ErrorQuiz --> Start
    ErrorEditQuiz --> Start

    %% Color Styling
    style Start fill:#f9f,stroke:#333,stroke-width:4px
    style RegistrationForm fill:#cde,stroke:#333,stroke-width:2px
    style ValidateRegistration fill:#FFC107,stroke:#fff,stroke-width:2px
    style CheckUserExist fill:#FFC107,stroke:#fff,stroke-width:2px
    style EncryptPassword fill:#FFC107,stroke:#fff,stroke-width:2px
    style AssignDefaultRole fill:#FFC107,stroke:#fff,stroke-width:2px
    style SaveUser fill:#FFC107,stroke:#fff,stroke-width:2px
    style LoginForm fill:#cde,stroke:#333,stroke-width:2px
    style ValidateLogin fill:#FFC107,stroke:#fff,stroke-width:2px
    style Authenticate fill:#FFC107,stroke:#fff,stroke-width:2px
    style GenerateToken fill:#FFC107,stroke:#fff,stroke-width:2px
    style SubmitQuizForm fill:#cde,stroke:#333,stroke-width:2px
    style ValidateQuiz fill:#FFC107,stroke:#fff,stroke-width:2px
    style CalculateMarks fill:#FFC107,stroke:#fff,stroke-width:2px
    style SaveQuizResult fill:#FFC107,stroke:#fff,stroke-width:2px
    style AddQuestion fill:#cde,stroke:#333,stroke-width:2px
    style SaveQuestion fill:#9f9,stroke:#333,stroke-width:2px
    style ErrorQuestion fill:#f99,stroke:#333,stroke-width:2px
    style EditQuestion fill:#cde,stroke:#333,stroke-width:2px
    style UpdateQuestion fill:#9f9,stroke:#333,stroke-width:2px
    style ErrorEditQuestion fill:#f99,stroke:#333,stroke-width:2px
    style RemoveQuestion fill:#f99,stroke:#333,stroke-width:2px
    style RetrieveQuizQuestions fill:#eef,stroke:#333,stroke-width:2px
    style CreateCategory fill:#cde,stroke:#333,stroke-width:2px
    style SaveCategory fill:#9f9,stroke:#333,stroke-width:2px
    style ErrorCategory fill:#f99,stroke:#333,stroke-width:2px
    style EditCategory fill:#cde,stroke:#333,stroke-width:2px
    style UpdateCategory fill:#9f9,stroke:#333,stroke-width:2px
    style ErrorEditCategory fill:#f99,stroke:#333,stroke-width:2px
    style RemoveCategory fill:#f99,stroke:#333,stroke-width:2px
    style DeleteAssociatedQuizzes fill:#fdd,stroke:#333,stroke-width:2px
    style CreateQuiz fill:#cde,stroke:#333,stroke-width:2px
    style SaveQuiz fill:#9f9,stroke:#333,stroke-width:2px
    style ErrorQuiz fill:#f99,stroke:#333,stroke-width:2px
    style EditQuiz fill:#cde,stroke:#333,stroke-width:2px
    style UpdateQuiz fill:#9f9,stroke:#333,stroke-width:2px
    style ErrorEditQuiz fill:#f99,stroke:#333,stroke-width:2px
    style RemoveQuiz fill:#f99,stroke:#333,stroke-width:2px
    style ViewQuizQuestions fill:#eef,stroke:#333,stroke-width:2px
    style ViewQuizResults fill:#eef,stroke:#333,stroke-width:2px
    style AnalyzeQuizPerformance fill:#eef,stroke:#333,stroke-width:2px
    style ViewAssociatedQuiz fill:#eef,stroke:#333,stroke-width:2px
    style ViewCategoryDetails fill:#eef,stroke:#333,stroke-width:2px
    style ViewAllQuestions fill:#eef,stroke:#333,stroke-width:2px
    style ViewAllCategories fill:#eef,stroke:#333,stroke-width:2px
