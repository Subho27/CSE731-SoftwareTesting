graph TD
    A[Start] --> B[Create Category]
    B -->|Valid Input| C[Save Category to Database]
    B -->|Invalid Input| D[Return Validation Error]
    C --> E[View All Categories]
    E --> F[Select a Category]
    F -->|View Details| G[View Category Details]
    F -->|Update Category| H[Edit Category]
    H -->|Valid Input| I[Update Category in Database]
    H -->|Invalid Input| J[Return Validation Error]
    F -->|Delete Category| K[Remove Category from Database]
    K -->|Cascade Delete Quizzes| L[Delete Associated Quizzes]
    G --> M[View Associated Quizzes]
    L --> E
    I --> E
    D --> A
    J --> A

    %% Minimal color styles
    style A fill:#f9f,stroke:#333,stroke-width:4px
    style B fill:#cde,stroke:#333,stroke-width:2px
    style C fill:#9f9,stroke:#333,stroke-width:2px
    style D fill:#f99,stroke:#333,stroke-width:2px
    style E fill:#eef,stroke:#333,stroke-width:2px
    style F fill:#cde,stroke:#333,stroke-width:2px
    style G fill:#eef,stroke:#333,stroke-width:2px
    style H fill:#cde,stroke:#333,stroke-width:2px
    style I fill:#9f9,stroke:#333,stroke-width:2px
    style J fill:#f99,stroke:#333,stroke-width:2px
    style K fill:#f99,stroke:#333,stroke-width:2px
    style L fill:#fdd,stroke:#333,stroke-width:2px
    style M fill:#eef,stroke:#333,stroke-width:2px
