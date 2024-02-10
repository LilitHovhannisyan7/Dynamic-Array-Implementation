import java.util.Iterator;

//Dynamic array:
//High priority functionality:
//push_back(val), pop_back(), insert(index, val), delete(index), search(val), resize(val, count), clear()
//Other functionality(can be implemented till next Friday),
//push_back(static array), push_back(val, count), insert(index, static array), delete(index, count)

class MyDynamicArray<T> implements Iterable<T>
{
    private int size;
    private int capacity;
    private T [] arr;

    public int getSize()
    {
        return this.size;
    }
    public int getCapacity()
    {
        return this.capacity;
    }
    public void enlarge() {
        if(this.capacity == 0) {
            this.capacity = 1;
        }
        this.capacity *= 2;
        T [] arr1 = (T[]) new Object[this.capacity];
        for(int i = 0; i < this.size; ++i) {
            arr1[i] = this.arr[i];
        }
        this.arr = arr1;

    }
    public void pushBack(T val) {
        if(this.size == this.capacity) {
            enlarge();
        }
        this.arr[this.size] = val;
        ++this.size;
    }
    public T popBack()
    {
        if(this.size == 0)
        {
            throw new RuntimeException("Invalid");
        }
        T temp = this.arr[this.size - 1];
        this.arr[this.size - 1] = null;    // null for garbage collection
        --this.size;
        return temp;
    }
    public void insert(T val, int index) {
        if(index < 0 || index > this.size)
        {
            throw new RuntimeException("Invalid index");
        }
        if(this.size == this.capacity) {
            enlarge();
        }
        if(index == this.size)
        {
            this.pushBack(val);
            return;
        }
        T [] arr1 = (T[]) new Object[this.capacity];
        int j = 0;
        for(int i = 0; i < index; ++i)
        {
            arr1[j] = this.arr[i];
            ++j;
        }
        arr1[j] = val;
        ++j;
        for(int i = index; i < this.size; ++i)
        {
            arr1[j] = this.arr[i];
            ++j;
        }

        this.arr = arr1;
        this.size = this.size + 1;
    }

    public T delete(int index)
    {
        if(index < 0 || index >= this.size)
        {
            throw new RuntimeException("Invalid index");
        }
        if(index == this.size - 1)
        {
            this.popBack();
        }
        T [] arr1 = (T[]) new Object[this.capacity];
        int j = 0;
        for(int i = 0; i < this.size; ++i)
        {
            if(i == index)
            {
                continue;
            }
            arr1[j] = this.arr[i];
            ++j;
        }
        T temp = arr[index];
        this.arr = arr1;
        --this.size;
        return temp;
    }
    public int search(T val)
    {
        for(int i = 0; i < this.size; ++i)
        {
            if(this.arr[i].equals(val))
            {
                return  i;
            }
        }
        return -1; //There is not val
    }

    public void shrink()
    {
        if(this.size < this.capacity)
        {
            this.capacity = (int)(this.size + (this.size * 0.25));
            T [] arr1 = (T[]) new Object[this.capacity];
            for(int i = 0; i < this.size; ++i)
            {
                arr1[i] = arr[i];
            }
            this.arr = arr1;
        }

    }

    public void resize(int count)
    {
        if(count > this.size || count < 0)
        {
            throw new RuntimeException("Invalid count");
        }
        this.size = count;
        T [] arr1 = (T[]) new Object[this.capacity];
        for(int i = 0; i < this.size; ++i)
        {
            arr1[i] = this.arr[i];
        }
        this.arr = arr1;
    }
    public void resize(int count, T val)
    {
        if(count < 0)
        {
            throw new RuntimeException("Invalid count");
        }
        if(count < this.size)
        {
            this.resize(count);
        }
        else if(count > this.size)
        {
            T [] arr1 = (T[]) new Object[this.capacity];
            int i = 0;
            for(; i < this.size; ++i)
            {
                arr1[i] = arr[i];
            }
            for(; i < count; ++i)
            {
                arr1[i] = val;
            }
            this.arr = arr1;
        }

    }


    private class ITR implements Iterator<T>
    {
        private int currentIndex = 0;
        @Override
        public boolean hasNext()
        {
            return this.currentIndex < size;
        }

        @Override
        public T next()
        {
            if(!hasNext())
            {
                throw new RuntimeException("Invalid");
            }
            T temp = arr[currentIndex];
            ++currentIndex;
            return temp;
        }

    }
    public Iterator<T> iterator()
    {
        return new ITR();
    }

    //    Modifications
    public void pushBack(T [] array)
    {
        for(int i = 0; i < array.length; ++i)
        {
            this.pushBack(array[i]);
        }
    }

    public void pushBack(T val, int count)
    {
        for(int i = 0; i < count; ++i)
        {
            this.pushBack(val);
        }
    }
    public void insert(T [] array, int index)
    {
        int temp = index;
        for(int i = 0; i < array.length; ++i)
        {
            this.insert(array[i], temp);
            ++temp;
        }
    }

    public void delete(int index, int count)
    {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Invalid index");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid count");
        }
        else if(count > 0)
        {
            int newSize = this.size - count;
            for (int i = index; i < newSize; i++)
            {
                this.arr[i] = this.arr[i + count];
            }
            this.size = newSize;
        }


    }
    @Override
    public String toString()
    {
        StringBuilder sBuild = new StringBuilder();
        for(int i = 0; i < this.size; ++i)
        {
            sBuild.append(this.arr[i] + " ");
        }
        return sBuild.toString();
    }
}


public class Main
{
    public static void main(String[] args)
    {
        MyDynamicArray<Integer> dynamicArray = new MyDynamicArray<>();
        dynamicArray.pushBack(1);
        dynamicArray.pushBack(2);
        dynamicArray.pushBack(3);
        System.out.println(dynamicArray);

        int poppedElement = dynamicArray.popBack();
        System.out.println(poppedElement);
        System.out.println(dynamicArray);

        dynamicArray.insert(4, 1);
        System.out.println(dynamicArray);

        int searchResult = dynamicArray.search(2);
        System.out.println(searchResult);

        dynamicArray.resize(2);
        System.out.println(dynamicArray);


        for (Integer element : dynamicArray) {
            System.out.print(element + " ");
        }
        System.out.println();

        Integer[] newArray = {5, 6, 7};
        dynamicArray.pushBack(newArray);
        System.out.println(dynamicArray);

        dynamicArray.pushBack(8, 3);
        System.out.println(dynamicArray);

        Integer[] insertArray = {9, 10};
        dynamicArray.insert(insertArray, 2);
        System.out.println(dynamicArray);

        dynamicArray.delete(1, 2);
        System.out.println(dynamicArray);




    }
}