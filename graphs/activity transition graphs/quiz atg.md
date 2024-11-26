graph TD
    A[Start] --> B[Create Quiz]
    B -->|Valid Input| C[Save Quiz to Database]
    B -->|Invalid Input| D[Return Validation Error]
    C --> E[View Quiz List]
    E --> F[Select a Quiz]
    F -->|View Details| G[View Quiz Details]
    F -->|Update Quiz| H[Edit Quiz]
    H -->|Valid Input| I[Update Quiz in Database]
    H -->|Invalid Input| J[Return Validation Error]
    F -->|Delete Quiz| K[Remove Quiz from Database]
    G --> L[View Associated Questions]
    G --> M[View Quiz Results]
    M --> N[Analyze Quiz Performance]
    K --> E
    I --> E
    N --> E
    D --> A

    %% Color styling
    style A fill:#f9f,stroke:#333,stroke-width:4px
    style B fill:#cde,stroke:#333,stroke-width:2px
    style C fill:#9f9,stroke:#333,stroke-width:2px
    style D fill:#f99,stroke:#333,stroke-width:2px
    style E fill:#eef,stroke:#333,stroke-width:2px
    style F fill:#cde,stroke:#333,stroke-width:2px
    style G fill:#9f9,stroke:#333,stroke-width:2px
    style H fill:#cde,stroke:#333,stroke-width:2px
    style I fill:#9f9,stroke:#333,stroke-width:2px
    style J fill:#f99,stroke:#333,stroke-width:2px
    style K fill:#f99,stroke:#333,stroke-width:2px
    style L fill:#eef,stroke:#333,stroke-width:2px
    style M fill:#eef,stroke:#333,stroke-width:2px
    style N fill:#eef,stroke:#333,stroke-width:2px
