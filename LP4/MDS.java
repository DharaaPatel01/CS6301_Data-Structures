/**
 * LP4 - Multi-dimensional Search
 *
 * @author Umar Khalid (uxk150630)
 * @author Dhara Patel (dxp190051)
 * @author Reetish Chand (rxg190006)
 * @author Rohan Vannala (rxv190003)
 */

package uxk150630;

// If you want to create additional classes, place them in this file as subclasses of MDS

import java.util.*;

public class MDS {
    // Add fields of MDS here
    private TreeMap<Long, Item> productTree;
    private HashMap<Long, HashSet<Item>> descriptionMap;

    public static class Item {
        private Long id;
        private HashSet<Long> description;
        Money price;

        public Item(long id, Money price, List<Long> list) {
            this.id = id;
            this.price = price;
            this.description = new HashSet<Long>();
            this.description.addAll(list);
        }

        public boolean addDesc(Long d) {
            return description.add(d);
        }

        public Set<Long> getDesc() {
            return description;
        }

        public void clearDesc() {
            description.clear();
        }

        public Money getPrice() {
            return price;
        }

        public void setPrice(Money p) {
            price = p;
        }

        public String toString() {
            return "[ID: " + id + ",\nPrice: " + price + ",\nDescription: " + description + "]";
        }
    }

    // Constructors
    public MDS() {
        productTree = new TreeMap<>();
        descriptionMap = new HashMap<>();
    }

    /**
     * a. Insert(id,price,list): insert a new item whose description is given
     *        in the list.  If an entry with the same id already exists, then its
     *        description and price are replaced by the new values, unless list
     *        is null or empty, in which case, just the price is updated.
     *
     * @param id
     * @param price
     * @param list
     * @return Returns 1 if the item is new, and 0 otherwise.
     */
    public int insert(long id, Money price, java.util.List<Long> list) {
        Item item = productTree.get(id);
        if (productTree.containsKey(id)) {
            if (!(list == null || list.isEmpty())) {

                //remove old item description from descriptionMap
                for (Long d: item.description) {
                    descriptionMap.get(d).remove(item);
                    if (descriptionMap.get(d).isEmpty())
                        descriptionMap.remove(d);
                }

                //remove old item description from productTree
                item.clearDesc();

                //insert new item description into productTree
                for (Long itemInList: list) {
                    item.addDesc(itemInList);
                }

                //insert new item description into descriptionMap
                for (Long d: item.description) {
                    if (descriptionMap.containsKey(d)) {
                        descriptionMap.get(d).add(item);
                    } else {
                        HashSet<Item> newHSet = new HashSet<>();
                        newHSet.add(item);
                        descriptionMap.put(d, newHSet);
                    }
                }
            }
            item.setPrice(price);

            return 0;
        }

        //Create new Item
        Item newItem = new Item(id, price, list);
        productTree.put(id, newItem); //product tree updated with new item

        //new item inserted into descriptionMap
        for (Long d: newItem.description) {
            if (descriptionMap.containsKey(d)) {
                descriptionMap.get(d).add(newItem);
            } else {
                HashSet<Item> newHSet = new HashSet<>();
                newHSet.add(newItem);
                descriptionMap.put(d, newHSet);
            }
        }
        return 1;
    }

    /**
     * b. Find(id): return price of item with given id (or 0, if not found).
     * @param id
     * @return price of item or 0 if not found
     */
    public Money find(long id) {
        Item item = productTree.get(id);
        if (productTree.containsKey(id)) {
            return item.getPrice();
        }
        return new Money();
    }

    /**
     * c. Delete(id): delete item from storage.
     *
     * @param id
     * @return Returns the sum of the
     *          long ints that are in the description of the item deleted,
     *          or 0, if such an id did not exist.
     */
    public long delete(long id) {
        Item item = productTree.get(id);
        long sum = 0;

        if (!productTree.containsKey(id)) {
            return 0;
        }
        for (Long desc: item.description) {
            //remove that desc from descriptionMap
            descriptionMap.get(desc).remove(item);
            if (descriptionMap.get(desc).isEmpty()) {
                descriptionMap.remove(desc);
            }
            sum += desc;
        }
        //remove item from productTree
        productTree.remove(id);

        return sum;
    }

    /**
     * d. FindMinPrice(n): given a long int, find items whose description
     *        contains that number (exact match with one of the long ints in the
     *        item's description), and return lowest price of those items.
     *        Return 0 if there is no such item.
     * @param n
     * @return lowest price of items or 0 if no such item
     */
    public Money findMinPrice(long n) {
        if (!descriptionMap.containsKey(n)) {
            return new Money();
        }

        HashSet<Item> tempSet = descriptionMap.get(n);
        Money min = new Money(Long.MAX_VALUE / 100, (int) (Long.MAX_VALUE % 100));
        for (Item item: tempSet) {
            if (item.getPrice().compareTo(min) < 0)
                min = item.getPrice();
        }
        return min;
    }

    /**
     * e. FindMaxPrice(n): given a long int, find items whose description
     *        contains that number, and return highest price of those items.
     *        Return 0 if there is no such item.
     * @param n
     * @return the highest price of those items or 0 if no such item
     */
    public Money findMaxPrice(long n) {
        if (!descriptionMap.containsKey(n)) {
            return new Money();
        }

        HashSet<Item> tempSet = descriptionMap.get(n);
        Money max = new Money();

        for (Item item: tempSet) {
            if (item.getPrice().compareTo(max) > 0)
                max = item.getPrice();
        }
        return max;
    }

    /**
     * f. FindPriceRange(n,low,high): given a long int n, find the number
     *        of items whose description contains n, and in addition,
     *        their prices fall within the given range, [low, high].
     * @param n
     * @param low
     * @param high
     * @return number of items whose price falls in range [low, high] and have description containing n
     */
    public int findPriceRange(long n, Money low, Money high) {
        int count = 0;
        if (high.compareTo(low) < 0) {
            return count;
        }

        HashSet<Item> tmp = descriptionMap.get(n);
        for (Item item: tmp) {
            count += !(item.price.compareTo(low) < 0 || item.price.compareTo(high) > 0) ? 1 : 0;
        }
        return count;
    }

    /**
     * g. PriceHike(l,h,r): increase the price of every product, whose id is
     *        in the range [l,h] by r%.  Discard any fractional pennies in the new
     *        prices of items.  Returns the sum of the net increases of the prices.
     * @param l
     * @param h
     * @param rate
     * @return sum of net increases of the prices
     */
    public Money priceHike(long l, long h, double rate) {
        if (l <= h) {
            Map<Long, Item> map = productTree.subMap(l, true, h, true);
            long totalPrice = 0;

            for (Map.Entry<Long, Item> product : map.entrySet()) {
                Item item = product.getValue();
                Money itemPrice = item.getPrice();
                long originalPrice = itemPrice.dollars()*100 + itemPrice.cents();
                long priceIncrease = (long) (Math.floor(originalPrice * rate)/100);
                long newPrice = originalPrice + priceIncrease;
                totalPrice += priceIncrease;
                Money priceInMoney = new Money(newPrice / 100, (int) (newPrice % 100));
                item.setPrice(priceInMoney);
            }
            return new Money(totalPrice / 100, (int) (totalPrice % 100));
        } else {
            return new Money();
        }
    }

    /**
     * h. RemoveNames(id, list): Remove elements of list from the description of id.
     *       It is possible that some of the items in the list are not in the
     *       id's description.  Return the sum of the numbers that are actually
     *       deleted from the description of id.  Return 0 if there is no such id.
     * @param id
     * @param list
     * @return sum of numbers that are actually deleted from the description of id or 0 if no such id
     */
    public long removeNames(long id, java.util.List<Long> list) {
        Item item = productTree.get(id);
        int count = 0;
        if(item == null) {
            return 0;
        } else {
            delete(id);
            for (long name : list) {
                if (item.getDesc().remove(name)) {
                    count += name;
                }
            }
            insert(id, item.getPrice(), new ArrayList<>(item.getDesc()));
            return count;
        }
    }

    // Do not modify the Money class in a way that breaks LP4Driver.java
    public static class Money implements Comparable<Money> {
        long d;
        int c;

        public Money() {
            d = 0;
            c = 0;
        }

        public Money(long d, int c) {
            this.d = d;
            this.c = c;
        }

        public Money(String s) {
            String[] part = s.split("\\.");
            int len = part.length;
            if (len < 1) {
                d = 0;
                c = 0;
            } else if (len == 1) {
                d = Long.parseLong(s);
                c = 0;
            } else {
                d = Long.parseLong(part[0]);
                c = Integer.parseInt(part[1]);
                if (part[1].length() == 1) {
                    c = c * 10;
                }
            }
        }

        public long dollars() {
            return d;
        }

        public int cents() {
            return c;
        }

        public int compareTo(Money other) {
            if (this.d == other.d) {
                if (this.c == other.c)
                    return 0;
                return this.c < other.c ? -1 : 1;
            }

            return this.d < other.d ? -1 : 1;
        }

        public String toString() {
            if (c < 10) return d + ".0" + c;
            return d + "." + c;
        }
    }
}