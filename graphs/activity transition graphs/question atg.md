graph TD
    A[Start] --> B[Add Question]
    B -->|Valid Input| C[Save Question to Database]
    B -->|Invalid Input| D[Return Validation Error]
    C --> E[View All Questions]
    E --> F[Select a Question]
    F -->|View Details| G[View Question Details]
    F -->|Update Question| H[Edit Question]
    H -->|Valid Input| I[Update Question in Database]
    H -->|Invalid Input| J[Return Validation Error]
    F -->|Delete Question| K[Remove Question from Database]
    F -->|Get Questions by Quiz| L[Retrieve Quiz Questions]
    G --> M[View Associated Quiz]
    I --> E
    L --> E
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
    style L fill:#eef,stroke:#333,stroke-width:2px
    style M fill:#eef,stroke:#333,stroke-width:2px
