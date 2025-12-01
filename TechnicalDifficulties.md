# Technical Challenges and Solutions – Graph Visualization Testing

## 1. Mocking the Graph Rendering Logic
**Challenge:**  
The graph module interacts with DOM elements and visualization utilities, which cannot run natively in the Jest environment.

**Solution:**
- Used `@testing-library/dom` and `jest.fn()` to mock DOM-related functionality.
- Replaced real rendering functions with mock implementations to validate behavior without requiring an actual browser.

---

## 2. Handling Asynchronous Data Loading
**Challenge:**  
Some graph functions depended on asynchronous fetching or processing of city-distance data.

**Solution:**
- Wrapped asynchronous calls using `async/await`.
- Implemented Jest mocks to simulate resolved and rejected promises.
- Ensured tests waited for the DOM updates using `waitFor()`.

---

## 3. Validating Edge Cases With Incomplete or Corrupted Data
**Challenge:**  
The dataset often contained missing fields, malformed structures, or null distances.

**Solution:**
- Added a dedicated test suite for invalid input scenarios.
- Implemented input guards in the module and asserted expected error messages or fallback behaviors.
- Ensured the graph gracefully handled empty or inconsistent lists.

---

## 4. Achieving 90%+ Coverage
**Challenge:**  
Some branches were difficult to reach, especially those handling unexpected situations or failures.

**Solution:**
- Wrote targeted tests to trigger rare branches such as:
    - Missing distance values
    - Repeated city names
    - Graph initialization failures
- Refactored the module slightly to improve testability and avoid overly coupled logic.

---

## 5. Mocking External Utility Libraries
**Challenge:**  
The graph relied on helper functions (e.g., distance calculators, formatting utilities) that were not directly testable.

**Solution:**
- Used `jest.mock()` to isolate external functions.
- Verified both:
    - That mocks were called with the right arguments
    - And that the graph behaved correctly even when utilities returned unexpected values

---

## 6. Running Jest With ES Modules
**Challenge:**  
The project required Jest to run using ES Modules, which triggered configuration issues with imports and experimental VM modules.

**Solution:**
- Enabled `NODE_OPTIONS=--experimental-vm-modules`.
- Updated Jest configuration to use `"type": "module"` and proper transform settings.
- Ensured compatibility across all test files.

---

## 7. Snapshot Testing Instability
**Challenge:**  
Snapshots changed frequently due to minor rendering differences, making them unreliable.

**Solution:**
- Limited snapshot tests to stable UI structures.
- Replaced volatile snapshots with explicit assertions for classes, nodes, and structure.
- Improved consistency by normalizing dynamic content before snapshotting.

---

## 8. Test Performance Constraints
**Challenge:**  
Graph calculations and mocks occasionally slowed down the test suite.

**Solution:**
- Cached heavy mocks using `jest.mock()` at the file level.
- Reduced unnecessary renders.
- Split large tests into smaller units.

---

## Summary
Despite several structural and environment-related challenges, applying mocks, async utilities, input validation tests, and improved Jest configuration enabled a stable, high-coverage test suite that supports scalability and maintainability.
