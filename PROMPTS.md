### SeverityIndicator Composable

**Prompt 1 (Initial Generation):**
> "Create a Jetpack Compose function named `SeverityIndicator` that takes a map of `SeverityGroup`
> to Float percentages and draws a circular donut chart using `Canvas` and `drawArc`."

**Prompt 2 (Refinement - Styling and Colors):**
> "Add a color mapping for the different `SeverityGroup` levels:
> - DEBUG: Color(0xFF607D8B)
> - INFO: Color(0xFF2196F3)
> - WARN: Color(0xFFFFC107)
> - ERROR: Color(0xFFFF5722)
> - FATAL: Color(0xFFE91E63)
> - UNKNOWN: Color(0xFF9E9E9E)
>
> Ensure the arc starts from the top (-90 degrees) and use a `Stroke` width of 8.dp."

**Prompt 3 (Refinement - Empty State):**
> "Update the `SeverityIndicator` to handle an empty map by drawing a light gray placeholder circle
> with low opacity."
