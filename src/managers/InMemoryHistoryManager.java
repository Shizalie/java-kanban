package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    // Узел двусвязного списка
    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
        }
    }

    private final List<Task> arrayList;
    private final Map<Integer, Node> hashMap;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        this.arrayList = new ArrayList<>();
        this.hashMap = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (arrayList.size() > 9) {
            removeOldest();
        }

        // Если задача уже существует, удаляем её из списка
        Node existingNode = hashMap.get(task.getId());
        if (existingNode != null) {
            removeNode(existingNode);
            arrayList.remove(existingNode.task);
        }

        // Добавляем задачу в список и HashMap
        arrayList.add(task);
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = hashMap.get(id);
        if (nodeToRemove != null) {
            arrayList.remove(nodeToRemove.task);
            removeNode(nodeToRemove);
            hashMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(arrayList);
    }

    // Добавление задачи в конец списка
    private void linkLast(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        hashMap.put(task.getId(), newNode);
    }

    // Удаление самой старой задачи из списка
    private void removeOldest() {
        Task oldestTask = arrayList.get(0);
        remove(oldestTask.getId());
    }

    // Удаление узла из связного списка
    private void removeNode(Node node) {
        if (node == head) {
            head = head.next;
        }
        if (node == tail) {
            tail = tail.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }
}

