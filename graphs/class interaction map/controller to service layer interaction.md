classDiagram

    class AuthController {
        +registerUser(RegisterRequest)
        +loginUser(LoginRequest)
    }

    class CategoryController {
        +addCategory(Category)
        +getCategories()
        +getCategory(Long)
        +updateCategory(Category)
        +deleteCategory(Long)
    }

    class QuestionController {
        +addQuestion(Question)
        +getQuestions()
        +getQuestion(Long)
        +updateQuestion(Question)
        +deleteQuestion(Long)
        +getQuestionsByQuiz(Long)
    }

    class QuizController {
        +addQuiz(Quiz)
        +getQuizzes()
        +getQuiz(Long)
        +updateQuiz(Quiz)
        +deleteQuiz(Long)
        +getQuizzesByCategory(Long)
    }

    class QuizResultController {
        +addQuizResult(QuizResult)
        +getQuizResults(Long)
    }

    class AuthServiceImpl {
        +registerUserService(User)
        +loginUserService(LoginRequest)
    }

    class CategoryServiceImpl {
        +addCategory(Category)
        +getCategories()
        +getCategory(Long)
        +updateCategory(Category)
        +deleteCategory(Long)
    }

    class QuestionServiceImpl {
        +addQuestion(Question)
        +getQuestions()
        +getQuestion(Long)
        +updateQuestion(Question)
        +deleteQuestion(Long)
        +getQuestionsByQuiz(Quiz)
    }

    class QuizServiceImpl {
        +addQuiz(Quiz)
        +getQuizzes()
        +getQuiz(Long)
        +updateQuiz(Quiz)
        +deleteQuiz(Long)
        +getQuizByCategory(Category)
    }

    class QuizResultServiceImpl {
        +addQuizResult(QuizResult)
        +getQuizResults()
        +getQuizResultsByUser(Long)
    }

    AuthController --> AuthServiceImpl : Calls
    CategoryController --> CategoryServiceImpl : Calls
    QuestionController --> QuestionServiceImpl : Calls
    QuizController --> QuizServiceImpl : Calls
    QuizResultController --> QuizResultServiceImpl : Calls

    AuthServiceImpl --> UserRepository : Uses
    CategoryServiceImpl --> CategoryRepository : Uses
    QuestionServiceImpl --> QuestionRepository : Uses
    QuestionServiceImpl --> QuizRepository : Uses
    QuizServiceImpl --> QuizRepository : Uses
    QuizServiceImpl --> CategoryRepository : Uses
    QuizResultServiceImpl --> QuizResultRepository : Uses
