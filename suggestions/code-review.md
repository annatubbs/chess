# Phase 0 code review suggestions

Scope: reviewed the current Java files in `shared/src/main/java/chess`, excluding `Main.java` and `ChessGame.java` as requested. Source files were not changed. I rechecked these suggestions against the current versions of the files.

## Highest-priority improvements

### 1. Preserve piece state when copying a board

`ChessBoard.deepCopy` now uses the correct 1-based `ChessPosition(rowIndex + 1, colIndex + 1)` coordinates and creates new `ChessPiece` instances, so the earlier coordinate and shared-reference concerns no longer apply. However, each copied piece is a plain `ChessPiece`, and a pawn's `firstMove` state is not copied. The copy therefore does not fully preserve piece-specific state: a pawn that has moved can be treated as fresh when `pieceMoves` constructs a new `Pawn` for move generation. Use the `PieceType.create(color)` factory (or another copy method on each piece) and explicitly preserve any state that affects future behavior.

### 2. Define pawn movement state and update it consistently

`Pawn.pieceMoves` no longer changes state just because moves were queried, which is an improvement. Its two-square advance still depends on mutable `firstMove`, while `didFirstMove()` must be called by the code that commits a move. Since `ChessPiece.pieceMoves` currently creates a temporary `Pawn` from the stored type and color on every call, that temporary pawn starts with `firstMove = true`; the original pawn's state is not consulted. Keep the movement-state owner and move-generation behavior in one place. For example, dispatch to the actual piece instance and update its state only when a move is made, or represent relevant history in the board/game state and pass it to move generation.

### 3. Make coordinate validity an explicit boundary invariant

`ChessPosition` accepts any row and column, while `ChessBoard.addPiece` and `getPiece` index the backing array directly. An invalid position can therefore fail later with an array-index exception. Validate positions in the constructor or at the public board boundary, and centralize the 1-to-8 / zero-based-index conversion so callers use one rule. `ChessPiece.canMoveHere` and `Pawn.canMoveHere` currently duplicate range checks; a shared validity helper would keep that behavior consistent.

### 4. Use one clear polymorphic model for piece behavior

`ChessPiece` stores a `PieceType` and has an `if/else` chain in `pieceMoves` that constructs a temporary subclass on each call, even though `King`, `Queen`, and the other concrete pieces already extend `ChessPiece`. This duplicates the type decision and bypasses state held by an actual piece instance. Prefer making move generation polymorphic on the actual concrete piece, or choose a single data-driven strategy/registry design. Avoid combining type dispatch with behavior-bearing subclasses.

## Maintainability and API design

### 5. Centralize movement direction data

Repeated direction vectors and `addAll` calls appear across `King`, `Queen`, `Rook`, `Bishop`, and `Knight`. Define named direction constants or shared helpers (orthogonal, diagonal, king-step, knight-step) to make rules easier to review and reduce copy/paste mistakes. Keep direction names consistent with the coordinate convention documented by `ChessPosition`.

### 6. Make board rendering easier to maintain

`ChessBoard.toString` repeatedly concatenates strings inside nested loops, creating many intermediate strings. Use a `StringBuilder`, append each cell and row in one pass, and define the display format clearly, including row order and labels if the output is intended for users.

### 7. Replace repeated numeric assumptions with named constants

The board size `8` and coordinate bounds recur throughout the board and move logic. Introduce a shared board-size constant and a small coordinate conversion helper. This documents the fixed dimensions and keeps bounds and indexing rules synchronized.

### 8. Clarify abstraction visibility and constructor intent

`SingleStepPiece` is public in the current version, as is `SlidePiece`; both expose public constructors even though they are abstract implementation bases. Choose visibility based on whether code outside `chess` should extend these types. Consider protected constructors if callers should only construct concrete pieces, while retaining any signatures required by the assignment.

### 9. Use descriptive names and JavaDoc that matches behavior

Names such as `firstMove`, `currPos`, `xyDirection`, and `canMoveHere` leave details implicit. Prefer names such as `hasMoved`, `candidatePosition`, `rowColumnOffset`, and `isSquareAvailableForMove`. Some comments say “return array” although the methods return a `Collection`. In `ChessPiece`, the JavaDoc above `canMoveHere` describes move calculation and a collection result, but the method returns a boolean; move or rewrite that documentation so it describes the method it precedes.

### 10. Keep imports and formatting consistent

Several piece classes import `ArrayList` without using it; remove unused imports. Apply consistent spacing, brace placement, and line wrapping (for example, constructor formatting and `new int[]{...}` spacing) so the code is easier to scan. An IDE formatter and compiler warnings can help enforce this consistently.

### 11. Make exception intent explicit

`InvalidMoveException` has an empty no-argument constructor. Either document why an empty message is useful or provide a stable default message. At call sites, include enough context to explain which move was rejected, while keeping presentation-specific wording out of core domain logic.

## Suggested sequence

1. Make board copies preserve concrete piece behavior and state.
2. Align pawn move generation, actual piece instances, and move-state updates.
3. Establish and reuse a single coordinate validity/indexing rule.
4. Simplify piece behavior ownership, then apply shared helpers, naming, documentation, and formatting improvements.

Preserve course-required method signatures. Once changes are made, check them against the supplied project tests.
