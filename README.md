# SE_lab_s8
# گام اول
---

## ۱. تعریف واسط (Interface) مستقل گراف

برای مستقل کردن کد از هر کتابخانه، یک واسط به نام `GraphAdapter` تعریف می‌کنیم که متدهای مورد نیاز را استانداردسازی می‌کند:

```java
public interface GraphAdapter {
    void addVertex(Integer v);
    void addEdge(String e, Integer v1, Integer v2);
    List<Integer> getNeighbors(Integer v);
}
```

---

## ۲. پیاده‌سازی Adapter برای JUNG

در این قدم، واسط را با استفاده از کلاس‌های کتابخانه JUNG پیاده‌سازی می‌کنیم:

```java
import edu.uci.ics.jung.graph.SparseMultigraph;

import java.util.ArrayList;
import java.util.List;

public class Adapter implements GraphAdapter {
        private final SparseMultigraph<Integer, String> graph = new SparseMultigraph<>();

        @Override
        public void addVertex(Integer v) {
            graph.addVertex(v);
        }

        @Override
        public void addEdge(String e, Integer v1, Integer v2) {
            graph.addEdge(e, v1, v2);
        }

        @Override
        public List<Integer> getNeighbors(Integer v) {
            return new ArrayList<>(graph.getNeighbors(v));
        }

}
```

---

## ۳. بازنویسی Traverserها (پیمایش‌گرها) برای استفاده از Adapter

کلاس‌های `DfsGraphTraverser` و `BfsGraphTraverser` بازنویسی شده‌اند تا فقط از واسط استفاده کنند و به JUNG وابسته نباشند. مثال BFS:

```java
public class BfsGraphTraverser implements Traverser {
    private final Adapter graph;

    public BfsGraphTraverser(Adapter graph) {
        this.graph = graph;
    }

    @Override
    public List<Integer> traverse(Integer startVertex) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startVertex);
        visited.add(startVertex);

        while (!queue.isEmpty()) {
            Integer vertex = queue.poll();
            result.add(vertex);

            List<Integer> neighbors = graph.getNeighbors(vertex);
            neighbors.sort(Comparator.naturalOrder());

            for (Integer neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }
}
```

---

## ۴. استفاده در بخش Main

در کلاس `Main`، از واسط و Adapter برای ساخت گراف و پیمایش با DFS و BFS استفاده می‌شود:

```java
public class Main {
    public static void main(String[] args) {
        Adapter graph = new Adapter();
        ...
        }
    }
```

---

## ۵. مزایای این ساختار

- تمام وابستگی‌ها به یک کتابخانه فقط در یک نقطه (Adapter) جمع شده است.
- مهاجرت به هر کتابخانه‌ی گراف دیگر فقط با اضافه کردن یک Adapter جدید انجام می‌شود.
- باقی کد پروژه ثابت و مستقل از نوع پیاده‌سازی گراف می‌ماند.

---

# گام دوم
---
همانطور که گفته شده، در این بخش صرفا به تغییر فایل adapter میپردازیم و انتظار داریم که کد به درستی کار کند و خروجی مشابه بخش قبلی دهد.
در واقع اگر دو فایل adapter داشتیم، لازم بود فقط import در فایل main را تغییر دهیم اما حالا که در دو فولدر جداگانه و دو adapter جداگانه نوشته‌ایم، صرفا محتویات داخل فایل adapter را تغییر میدهیم.

---
## پیاده سازی Adapter برای JGraphT
در adapter جدید از کتابخانه مربوط به jGraphT، کلاس SimpleGraph را import میکنیم و توابع موجود در interface مربوط به graph را طبق توابع موجود در کلاس SimpleGraph پیاده سازی میکنیم.

```java
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Adapter implements GraphAdapter {
    private final SimpleGraph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    @Override
    public void addVertex(Integer v) {
        graph.addVertex(v);
    }

    @Override
    public void addEdge(String e, Integer v1, Integer v2) {
        graph.addEdge(v1, v2);
    }

    @Override
    public List<Integer> getNeighbors(Integer v) {
        Set<DefaultEdge> edges = graph.edgesOf(v);
        List<Integer> neighbors = new ArrayList<>();
        for (DefaultEdge edge : edges) {
            Integer source = graph.getEdgeSource(edge);
            Integer target = graph.getEdgeTarget(edge);
            if (source.equals(v)) {
                neighbors.add(target);
            } else {
                neighbors.add(source);
            }
        }
        return neighbors;
    }

```

---

همانطور که در تصویر زیر مشاهده میکنید، خروجی کد main برای هر دو Adapter مشابه است:

![Sample output](i1.png)

---

# گام سوم
---
ابتدا توضیحی درباره الگوی استراتژی؛ الگوی استراتژی جایی به کار میرود که الگوریتمی داشته باشیم که بخواهیم در زمان runtime، امکان تغییر آن بصورت dynamic وجود داشته باشد.
برای پیاده سازی این الگو، نیاز به interface و تابعی داریم که الگوریتمی را برای هدف خاصی پیاده کرده است. در ادامه میتوانیم به تعداد الگوریتم هایی که قصد پیاده کردن آنها را داریم، پیاده سازی های متفاوتی از interface ارائه دهیم.
در نتیجه خروجی کار یکیست اما الگوریتم های در هر پیاده سازی، متفاوت است.

در این پروژه traverser یک اینترفیس است که یک تابع traverse دارد که قرار است به عنوان خروجی پیمایشی از یک گراف ارائه دهد. این پیمایش می‌تواند طبق الگوریتم‌های متعددی انجام شود که به ازای دو مورد از آنها یعنی BFS و DFS پیاده‌سازی‌هایی از این اینترفیس داریم که هر گدام پیمایش را طبق الگوریتم خود انجام می‌دهد و به عنوان خروجی یک پیمایش از گراف ارائه می‌دهد.

استفاده از این الگو به این دلیل قابل قبول است که چون پیمایش گراف خروجی واحدی خواهد داشت  تنها الگوریتم آن تفاوت می‌کند، استفاده از این الگو منطقی است. یعنی هر موقع پیمایشی از گراف بخواهیم، هر پیاده سازی از interface جوابگوست و در رانتایم نیز میتوانیم الگوریتم مناسب خودمان را انتخاب کنیم.