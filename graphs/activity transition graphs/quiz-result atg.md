graph TD
    A[Start] --> B[Submit Quiz]
    B -->|Valid Submission| C[Evaluate Answers]
    C -->|Calculate Marks| D[Generate Quiz Result]
    D --> E[Save Result to Database]
    E --> F[Get Results by User]
    F --> G[Display User Results]
    E --> H[Get All Results]
    H --> I[Display All Results]
    B -->|Invalid Submission| J[Return Error]
    C -->|Error During Evaluation| K[Return Error]

    %% Minimal color styles
    style A fill:#f9f,stroke:#333,stroke-width:4px
    style B fill:#cde,stroke:#333,stroke-width:2px
    style C fill:#9f9,stroke:#333,stroke-width:2px
    style D fill:#eef,stroke:#333,stroke-width:2px
    style E fill:#cde,stroke:#333,stroke-width:2px
    style F fill:#eef,stroke:#333,stroke-width:2px
    style G fill:#eef,stroke:#333,stroke-width:2px
    style H fill:#cde,stroke:#333,stroke-width:2px
    style I fill:#eef,stroke:#333,stroke-width:2px
    style J fill:#f99,stroke:#333,stroke-width:2px
    style K fill:#f99,stroke:#333,stroke-width:2px
