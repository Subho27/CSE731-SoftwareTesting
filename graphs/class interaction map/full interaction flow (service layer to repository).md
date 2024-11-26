classDiagram

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

    class UserDetailsServiceImpl {
        +loadUserByUsername(String)
    }

    class JwtUtil {
        +generateToken(UserDetails)
    }

    class AuthenticationManager {
        +authenticate(UsernamePasswordAuthenticationToken)
    }

    class RoleRepository {
        +findById(String)
    }

    class UserRepository {
        +findByUsername(String)
        +findUserByUserId(Long)
    }

    class CategoryRepository {
        +save(Category)
        +findAll()
        +findById(Long)
    }

    class QuestionRepository {
        +save(Question)
        +findAll()
        +findByQuiz(Quiz)
    }

    class QuizRepository {
        +save(Quiz)
        +findAll()
        +findByCategory(Category)
    }

    class QuizResultRepository {
        +save(QuizResult)
        +findAll()
        +findByUserId(Long)
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
    CategoryRepository --> Category : Returns
    QuestionRepository --> Question : Returns
    QuizRepository --> Quiz : Returns
    QuizResultRepository --> QuizResult : Returns
    UserRepository --> User : Returns
