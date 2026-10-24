# AI Review of ParcelPoint Starter Code

## Files Read

- `Main.java`
- `Locker.java`
- `Parcel.java`
- `ParcelService.java`

## Code Quality, Cohesion, Coupling, Connascence, and Encapsulation Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Code quality | `Main.java:8` | The Scanner is not explicitly closed. | Medium | Close the Scanner when the program finishes, considering that it wraps `System.in`. |
| 2 | Code quality | `Main.java:10` | Input is split using a single space, and command arguments are not validated before use. | High | Normalize whitespace and validate command arguments before accessing them. |
| 3 | Encapsulation | `Parcel.java:8` | The `status` field is public, allowing other classes to change it directly. | Medium | Make the field private and provide controlled access methods. |
| 4 | Code quality | `ParcelService.java:6` | The `Date` import is unused. | Low | Remove the unused import. |
| 5 | Code quality | `ParcelService.java:12` | The `maxParcels` field is declared but never used. | Medium | Remove the field or use it to enforce a parcel limit. |
| 6 | Encapsulation | `ParcelService.java:18` | `getLockers()` returns the internal mutable list, allowing callers to modify the service's locker collection. | Medium | Return an unmodifiable view or a defensive copy. |
| 7 | Code quality | `ParcelService.java:42` | The parcel code is compared using `==`, which compares object references instead of string values. | High | Use `.equals()` to compare string values. |
| 8 | Code quality | `ParcelService.java:74–79` | The `FileWriter` is not closed, and the empty catch block hides file-writing errors. | High | Use try-with-resources and handle or report exceptions. |
| 9 | Coupling / Encapsulation | `Parcel.java:8`, `ParcelService.java:28,44,63` | `ParcelService` directly accesses and changes the public `status` field, coupling it to the parcel's internal representation. | Medium | Encapsulate status changes within `Parcel`. |
| 10 | Coupling / Cohesion | `Main.java:17,19` | `printParcels(boolean forStaff)` changes its behavior based on a control flag. This is control coupling and may indicate logical cohesion. | Medium | Consider separate methods for staff and ordinary parcel listings. |
| 11 | Cohesion | `ParcelService.java:23–35` | `storeParcel()` handles locker allocation, status changes, notification output, and file persistence. | Medium | Separate notification and persistence responsibilities where appropriate. |
| 12 | Class cohesion | `ParcelService.java:14–80` | `ParcelService` manages locker allocation, parcel status, fee calculation, printing, notifications, and file persistence. These responsibilities may be better separated. | Medium | Split responsibilities into focused classes if justified by the project requirements. |
| 13 | Connascence | `Parcel.java:15`, `ParcelService.java:28,44,63` | Parcel status is represented by string literals shared across classes, creating connascence of convention. | Medium | Consider an enum for parcel status. |

## Summary

The main concerns are incorrect string comparison, weak input validation, resource handling, an empty exception handler, public mutable state, and the concentration of several responsibilities in `ParcelService`.

This review is AI-generated. Findings should be checked against the source code, the course definitions, and SonarQube results before being used in the final report.
