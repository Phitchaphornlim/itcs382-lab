# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/Phitchaphornlim/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | Eclipse Temurin 25.0.4.1 LTS |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code |
| SonarQube for IDE |  |
| AI tool and exact model | ChatGPT, AI VS Code |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 91c07dc

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 7 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 8 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 9 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 10 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 2026-10-24 |
| AI tool and exact model | ChatGPT, GPT-6 |
| Commit reviewed | 91c07dc |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Pasted the four Java files into ChatGPT |
| Output file in the reviews folder | reviews/parcelpoint-ai-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:42 | String comparison uses == instead of .equals(). | AI | Code quality | Yes. == compares object references instead of string values. |
| 2 | ParcelService.java:74–79 | The FileWriter is not closed, and the catch block is empty. | Both | java:S108; resource handling | Yes. The writer should be closed and file-writing errors should be handled. |
| 3 | ParcelService.java:12 | maxParcels is declared but never used. | Both | java:S1068 | Yes. The field is not used in the provided code. |
| 4 | Parcel.java:8 | The status field is public and can be modified directly by other classes. | AI | Encapsulation | Yes. The field is accessible outside the class. |
| 5 | ParcelService.java:18 | getLockers() returns the internal mutable list. | AI | Encapsulation | Yes. Callers can modify the returned list. |
| 6 | Main.java:10 | Input arguments are not validated before use. | AI | Input validation | Yes. Missing arguments or invalid values can cause errors. |
| 7 | ParcelService.java:23–35 | storeParcel() handles storage, status changes, notification output, and file saving. | AI | Cohesion; single responsibility | Partly. It performs several responsibilities, but whether to split them depends on design requirements. |
| 8 | ParcelService.java:14–80 | ParcelService handles locker allocation, fees, printing, and file persistence. | AI | Class cohesion | Partly. The class has several responsibilities, but this finding alone does not prove the design must be changed. |

2.1 Name two problems that only the AI review found.

1. The status field in Parcel is public, allowing other classes to modify it directly.
2. getLockers() returns the internal mutable list, allowing callers to modify the locker collection.

2.2 Name one problem that only SonarQube found, or write none.

None identified so far. This must be confirmed by comparing the actual AI review output with the SonarQube results.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

The finding that ParcelService has weak class cohesion is somewhat vague. The class has several responsibilities, but the finding does not identify a specific cohesion type or prove that the design must be changed. I checked the methods in ParcelService and found that it handles locker allocation, fee calculation, printing, and file persistence.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Fixed | Replaced `System.out` with appropriate output handling. |
| 2 | Fixed | Replaced `System.out` with appropriate output handling. |
| 3 | Fixed | Replaced `System.out` with appropriate output handling. |
| 4 | Fixed | Replaced `System.out` with appropriate output handling. |
| 5 | Fixed | Changed date and time handling to use the `java.time` API. |
| 6 | Fixed | Removed the unused `maxParcels` field. |
| 7 | Fixed | Replaced `System.out` with appropriate output handling. |
| 8 | Fixed | Replaced `System.out` with appropriate output handling. |
| 9 | Fixed | Replaced `System.out` with appropriate output handling. |
| 10 | Fixed | Added error handling to the empty `catch` block. |

Number of SonarQube issues that remain after your fixes: 2

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Poor encapsulation | Changed the status field in Parcel from public to private and added getter and setter methods. | Check Git commit hash |
| Exposed internal mutable data | Changed getLockers() to return an unmodifiable list using Collections.unmodifiableList(). | Check Git commit hash |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 2026-10-24 | 2026-10-24 |
| AI tool and exact model | Enter the actual AI tool and model used | Enter the actual AI tool and model used |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B | Prompt C |
| Builds with mvn package (yes or no) | Yes | Yes |
| Number of classes, interfaces, enums and records | 1 class, 0 interfaces, 0 enums, 0 records | 6 classes, 1 interface, 1 enum, 0 records |
| SonarQube issues, total | Enter the actual scan result | Enter the actual scan result |
| SonarQube issues, by rule | Enter the actual scan result | Enter the actual scan result |
| AI review findings in Check 1 | Enter the actual review result | Enter the actual review result |
| AI review findings in Check 2 | Enter the actual review result | Enter the actual review result |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

SonarQube may not report cohesion or coupling problems because these are design issues that require human judgment.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes. bikeshare-poor puts multiple responsibilities in one Main class and uses public static fields, while bikeshare-good separates responsibilities into different classes.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

I would choose bikeshare-good because its classes have clear responsibilities, making it easier to add a Visitor member type and update the fee rules.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

SonarQube checks code quality issues, such as unused fields and empty catch blocks. It does not fully judge design quality, such as cohesion and coupling.

5.2 When should you not trust the AI review? Give an example from this lab.

I should not trust the AI review without checking the code. For example, the AI may describe a design as having poor cohesion without enough evidence.

5.3 What will you do differently in Project Phase 2 after this lab?

In Project Phase 2, I will check cohesion and coupling, encapsulate fields, handle exceptions properly, and test AI suggestions before applying them.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
|---|---|---|---|
| ChatGPT, GPT-6 | Code review and report writing | Review comments and report text | Compared suggestions with the source code, build results, and test output |
| [Actual AI tool and model used in VS Code] | Generate and review BikeShare designs | `bikeshare-poor` and `bikeshare-good` source files | Built with Maven and reviewed the generated code |
