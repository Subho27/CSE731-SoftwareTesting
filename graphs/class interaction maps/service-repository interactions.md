classDiagram

    class UserRepository {
        +findByUsername(String)
        +findUserByUserId(Long)
    }

    class RoleRepository {
        +findById(String)
    }

    class CategoryRepository {
        +save(Category)
        +findAll()
        +findById(Long)
        +delete(Category)
    }

    class QuestionRepository {
        +save(Question)
        +findAll()
        +findByQuiz(Quiz)
        +delete(Question)
    }

    class QuizRepository {
        +save(Quiz)
        +findAll()
        +findByCategory(Category)
        +deleteById(Long)
    }

    class QuizResultRepository {
        +save(QuizResult)
        +findAll()
        +findByUserId(Long)
    }

    class Role {
        +id
        +name
    }

    class User {
        +username
        +password
        +roles
    }

    RoleRepository --> Role : Returns
    CategoryRepository --> Category : Manages
    QuestionRepository --> Question : Manages
    QuizRepository --> Quiz : Manages
    QuizResultRepository --> QuizResult : Manages
    UserRepository --> User : Manages
    UserRepository --> RoleRepository : Uses
