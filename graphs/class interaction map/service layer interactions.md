classDiagram

    class AuthServiceImpl {
        +registerUserService(User)
        +loginUserService(LoginRequest)
        -authenticate(username, password)
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

    class UserDetailsServiceImpl {
        +loadUserByUsername(String)
    }

    class JwtUtil {
        +generateToken(UserDetails)
    }

    class AuthenticationManager {
        +authenticate(UsernamePasswordAuthenticationToken)
    }

    AuthServiceImpl --> UserRepository : Uses
    AuthServiceImpl --> JwtUtil : Uses
    AuthServiceImpl --> AuthenticationManager : Uses
    CategoryServiceImpl --> CategoryRepository : Uses
    QuestionServiceImpl --> QuestionRepository : Uses
    QuestionServiceImpl --> QuizRepository : Uses
    QuizServiceImpl --> QuizRepository : Uses
    QuizServiceImpl --> CategoryRepository : Uses
    QuizResultServiceImpl --> QuizResultRepository : Uses
    UserDetailsServiceImpl --> UserRepository : Uses
    JwtUtil --> AuthenticationManager : Uses
