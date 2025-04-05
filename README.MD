# Project Refactoring for PLM Software

## Overview

This project involves refactoring a Proof of Concept (POC) for a PLM (Product Lifecycle Management) software editor. The software is designed to manage data related to product conception, including parts and documents. The refactoring aims to ensure data integrity, enforce business rules, and improve code maintainability.

## Technical Stack

- Java
- Spring
- Hibernate

## Changes Made

### 1. Application Configuration

- Removed redundant `@EnableAutoConfiguration` annotation from `Application.java`.
- Fixed the autowired method in `Application.java`.

### 2. Service Layer Refactoring

- **DocumentService.java** and **PartService.java**:
    - Enforced business rules in service methods.
    - Added explicit reasons for service failures.
    - Ensured data integrity by correctly updating linked documents when a part is updated.
    - Created custom exceptions to handle business rule violations.

### 3. Exception Handling

- Added exceptions to handle business rules violations

### 4. Controller Layer

- Updated `DocumentController.java` and `PartController.java` to use the refactored service methods.

### 5. Dependency Injection
- Ensure more optimal dependency injection in `DocumentService.java` and `PartService.java`.Replace field injection with constructor injection for better testability and maintainability.


## Future Improvements

1. **Data Integrity**: Continue to ensure data integrity across all entities.
2. **Performance**: Optimize performance while maintaining code simplicity. Introduce custom exceptions
3. **New Entities**: Add new entities with similar behaviors as parts and documents.
4. **Global Customers**: Ensure the solution is adaptable for global customers.
5. **Integration**: Make services easy to reuse for developers.

## Not Included

- User management (creation, login, etc.)
- Web security or any security-related topics
- Spring configuration (data source, Hibernate, etc.)
- REST services input/output kind (current controller methods signature are ok as-is)
- Build configuration (no Maven or Gradle)

## Deliverables

- Java source code only
- Any other kind of content can be just described
