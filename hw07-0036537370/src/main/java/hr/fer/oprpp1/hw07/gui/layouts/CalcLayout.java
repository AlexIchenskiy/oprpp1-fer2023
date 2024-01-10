package hr.fer.oprpp1.hw07.gui.layouts;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

/**
 * Class representing a custom calculated layout.
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * Max number of rows of layout, counting from 1.
     */
    private final int ROWS = 5;

    /**
     * Number of columns of the first grid item.
     */
    private final int FIRST_ITEM_COLUMNS = 5;

    /**
     * Max number of columns of layout, counting from 1.
     */
    private final int COLUMNS = 7;

    /**
     * Map representing all layout components by their position.
     */
    private final Map<RCPosition, Component> components = new HashMap<>();

    /**
     * Map representing a size getter function by size type.
     */
    private final Map<CalcLayoutSizeType, Function<Component, Dimension>> getFunctionByType = Map.of(
            CalcLayoutSizeType.MAXIMUM, Component::getMaximumSize,
            CalcLayoutSizeType.MINIMUM, Component::getMinimumSize,
            CalcLayoutSizeType.PREFERRED, Component::getPreferredSize
            );

    /**
     * Gap between the layout components.
     */
    private int gap;

    /**
     * Creates a layout with provided gap.
     * @param gap Layout gap
     */
    public CalcLayout(int gap) {
        if (gap < 0) {
            throw new CalcLayoutException("Gap must be positive!");
        }

        this.gap = gap;
    }

    /**
     * Creates a layout without a gap.
     */
    public CalcLayout() {
        this(0);
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp        the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        Objects.requireNonNull(comp, "Component cant be null!");
        Objects.requireNonNull(constraints, "Constraints cant be null!");

        RCPosition pos = null;

        if (constraints instanceof RCPosition) pos = (RCPosition) constraints;

        if (constraints instanceof String) {
            try {
                pos = RCPosition.parse(constraints.toString());
            } catch (Exception e) {
                throw new CalcLayoutException("Invalid constraints format!");
            }
        }

        if (pos == null) throw new CalcLayoutException("Invalid constraints type!");

        int row = pos.getRow();
        int column = pos.getColumn();

        if (row < 1 || row > this.ROWS) throw new CalcLayoutException("Row value must be in [1, 5] range!");
        if (column < 1 || column > this.COLUMNS) throw new CalcLayoutException("Column value must be in [1, 7] range!");
        if (row == 1 && column > 1 && column < this.COLUMNS - 1) throw new CalcLayoutException("Acceptable values " +
                "for first row are only (1, 1), (1, 6) and (1, 7)!");

        if (this.components.containsKey(pos)) throw new CalcLayoutException("This position is already used!");
        if (this.components.containsValue(comp)) throw new CalcLayoutException("One component must have single " +
                "constraint!");

        this.components.put(pos, comp);
    }

    /**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     *
     * @param target the target container
     * @return the maximum size of the container
     * @see Component#getMaximumSize
     * @see LayoutManager
     */
    @Override
    public Dimension maximumLayoutSize(Container target) {
        return this.getLayoutSize(target, CalcLayoutSizeType.MAXIMUM);
    }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * @param target the target container
     * @return the x-axis alignment preference
     */
    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     *
     * @param target the target container
     * @return the y-axis alignment preference
     */
    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     *
     * @param target the target container
     */
    @Override
    public void invalidateLayout(Container target) {}

    /**
     * If the layout manager uses a per-component string,
     * adds the component {@code comp} to the layout,
     * associating it
     * with the string specified by {@code name}.
     *
     * @param name the string to be associated with the component
     * @param comp the component to be added
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException("Unsupported component adding method.");
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        Objects.requireNonNull(comp, "Component cant be null!");
        this.components.values().remove(comp);
    }

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the container to be laid out
     * @return the preferred dimension for the container
     * @see #minimumLayoutSize
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return this.getLayoutSize(parent, CalcLayoutSizeType.PREFERRED);
    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the component to be laid out
     * @return the minimum dimension for the container
     * @see #preferredLayoutSize
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return this.getLayoutSize(parent, CalcLayoutSizeType.MINIMUM);
    }

    /**
     * Lays out the specified container.
     *
     * @param parent the container to be laid out
     */
    @Override
    public void layoutContainer(Container parent) {
        Objects.requireNonNull(parent, "Parent cant be null!");

        Insets insets = parent.getInsets();
        List<Integer> widths = getUniformlyDistributedSizes(this.COLUMNS, parent.getWidth(), insets.left, insets.right);
        List<Integer> heights = getUniformlyDistributedSizes(this.ROWS, parent.getHeight(), insets.top, insets.bottom);

        int widthOffset = insets.top + this.gap / 2;
        int heightOffset = insets.left + this.gap / 2;

        for (int row = 1; row <= this.ROWS; row++) {
            for (int column = 1; column <= this.COLUMNS; column++) {
                Component component = this.components.get(new RCPosition(row, column));

                if (component != null) {
                    if (row == 1 && column == 1) {
                        int firstItemTotalWidth =
                                widths.stream().limit(this.FIRST_ITEM_COLUMNS).mapToInt(Integer::intValue).sum()
                                        + (this.FIRST_ITEM_COLUMNS - 1) * this.gap;
                        component.setBounds(widthOffset, heightOffset, firstItemTotalWidth, heights.get(0));
                        // widthOffset += firstItemTotalWidth - widths.get(0);
                    } else {
                        component.setBounds(widthOffset, heightOffset, widths.get(column - 1), heights.get(row - 1));
                    }
                }

                if (column == this.COLUMNS) {
                    widthOffset = insets.left + this.gap / 2;
                    heightOffset += heights.get(row - 1) + this.gap;
                } else {
                    widthOffset += widths.get(column - 1) + this.gap;
                }
            }
        }
    }

    /**
     * Function that returns a layout size (dimension) of the given container of the given type.
     * @param container Container of needed dimensions
     * @param sizeType Type of the container size
     * @return Corresponding container dimensions
     */
    private Dimension getLayoutSize(Container container, CalcLayoutSizeType sizeType) {
        int maxWidth = -1;
        int maxHeight = -1;

        for (Map.Entry<RCPosition, Component> entry : this.components.entrySet()) {
            int width = (entry.getKey().equals(new RCPosition(1, 1)))
                    ? (getFunctionByType.get(sizeType).apply(entry.getValue()).width - this.gap *
                        (this.FIRST_ITEM_COLUMNS - 1)) / this.FIRST_ITEM_COLUMNS
                    : getFunctionByType.get(sizeType).apply(entry.getValue()).width;

            int height = getFunctionByType.get(sizeType).apply(entry.getValue()).height;

            maxWidth = Math.max(maxWidth, width);
            maxHeight = Math.max(maxHeight, height);
        }

        if (maxWidth == -1 || maxHeight == -1) return null;

        Insets insets = container.getInsets();

        return new Dimension(
                maxWidth * COLUMNS + this.gap * (this.COLUMNS - 1) + insets.left + insets.right,
                maxHeight * ROWS + this.gap * (this.ROWS - 1) + insets.bottom + insets.top
        );
    }

    /**
     * Function to get the list of the uniformly distributed sizes between all elements.
     * @param numberOfElements Number of elements to be distributed
     * @param availableSpace Available space
     * @param firstInset First element inset (e.g. left or bottom)
     * @param secondInset Second element inset (e.g. right or top)
     * @return List of the uniformly distributed sizes between all elements
     */
    private List<Integer> getUniformlyDistributedSizes(int numberOfElements, int availableSpace,
                                                       int firstInset, int secondInset) {
        List<Integer> uniformlyDistributedSizes = new ArrayList<>();
        int availableSpaceTrimmed = availableSpace - this.gap * numberOfElements - firstInset - secondInset;

        if (availableSpaceTrimmed % numberOfElements == 0) {
            uniformlyDistributedSizes.addAll(
                    Collections.nCopies(numberOfElements, availableSpaceTrimmed / numberOfElements));

            return uniformlyDistributedSizes;
        }

        int baseValue = availableSpaceTrimmed / numberOfElements;
        int remainder = availableSpaceTrimmed % numberOfElements;
        int count = numberOfElements / remainder;

        for (int i = 0; i < numberOfElements; i++) {
            int additionalValue = i % count == 0 ? 1 : 0;
            uniformlyDistributedSizes.add(baseValue + additionalValue);
            remainder -= additionalValue;
        }

        return uniformlyDistributedSizes;
    }

}
