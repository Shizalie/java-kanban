package managers;

import tasks.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> hashMap;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        this.hashMap = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        // Если задача уже существует, удаляем её из списка
        Node existingNode = hashMap.get(task.getId());
        if (existingNode != null) {
            removeNode(existingNode);
        }

        // Добавляем задачу в список и HashMap
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = hashMap.get(id);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
            hashMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        // Создаем список итерацией по связному списку
        // так как порядок важен, используем LinkedList
        LinkedList<Task> list = new LinkedList<>();
        Node current = head;
        while (current != null) {
            list.add(current.task);
            current = current.next;
        }
        return list;
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

    // Узел двусвязного списка
    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
        }
    }
}
