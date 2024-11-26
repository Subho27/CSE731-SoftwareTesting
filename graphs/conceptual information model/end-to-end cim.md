classDiagram

    class Category {
        +Long id
        +String name
        +String description
    }

    class Question {
        +Long id
        +String questionText
        +String answerOptions
        +String correctAnswer
        +Quiz quiz
    }

    class Quiz {
        +Long id
        +String title
        +String description
        +Category category
        +List<Question> questions
    }

    class QuizResult {
        +Long id
        +Long userId
        +Long quizId
        +int score
        +Date dateTaken
        +User user
        +Quiz quiz
    }

    class Role {
        +String roleName
        +String description
    }

    class User {
        +Long userId
        +String username
        +String password
        +String email
        +List<Role> roles
        +List<QuizResult> quizResults
    }

    %% Relationships
    Category "1" -- "0..*" Quiz : contains
    Quiz "1" -- "0..*" Question : has
    Quiz "1" -- "0..*" QuizResult : linked to
    User "1" -- "0..*" QuizResult : has
    User "1" -- "0..*" Role : assigned to
    QuizResult "0..*" -- "1" User : belongs to
    QuizResult "0..*" -- "1" Quiz : is for
