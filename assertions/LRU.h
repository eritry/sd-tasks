#ifndef SD_TASK1_LRU_H
#define SD_TASK1_LRU_H

#include <unordered_map>
#include <cassert>

template<typename K, typename T>
class LRUCache {
public:
    explicit LRUCache(size_t capacity = 128);

    std::optional<T> get(const K& key);

    void add(const K& key, const T& value);

    void erase(const K& key);

private:
    void removeKey(const K& key);

    void pushBack(const K& key, const T& value);

    void assertSizeCorrect();

    void assertKeySynchronized(const K& key);

    void assertKeyExist(const K& key);

    void assertKeyNotExist(const K& key);

    void assertFirstLastSynchronized();

    size_t capacity;
    std::optional<K> first, last; // голова и хвост
    std::unordered_map<K, T> values; // хешмап
    std::unordered_map<K, std::pair<std::optional<K>, std::optional<K>>> links; // ссылки для двусвязного списка
};

template<typename K, typename T>
void LRUCache<K, T>::assertSizeCorrect() {
    assert(links.size() == values.size() && links.size() <= capacity);
}

template<typename K, typename T>
void LRUCache<K, T>::assertKeySynchronized(const K &key) {
    assert((values.find(key) == values.end()) == (links.find(key) == links.end()));
}

template<typename K, typename T>
void LRUCache<K, T>::assertKeyExist(const K& key) {
    assert(links.find(key) != links.end() && values.find(key) != values.end());
}

template<typename K, typename T>
void LRUCache<K, T>::assertKeyNotExist(const K& key) {
    assert(links.find(key) == links.end() && values.find(key) == values.end());
}


template<typename K, typename T>
void LRUCache<K, T>::assertFirstLastSynchronized() {
    assert((first == std::nullopt) == (last == std::nullopt));
}

template<typename K, typename T>
LRUCache<K, T>::LRUCache(size_t capacity) : capacity(capacity) {
    assert(capacity > 0);
    values.reserve(capacity);

    first = last = std::nullopt;
    links.reserve(capacity);

    assertSizeCorrect();
}

template<typename K, typename T>
std::optional<T> LRUCache<K, T>::get(const K& key) {
    assertSizeCorrect();
    assertKeySynchronized(key);

    if (values.find(key) == values.end()) {
        return std::nullopt;
    }

    auto tmp = values[key];
    removeKey(key);
    pushBack(key, tmp);
    return values[key];
}

template<typename K, typename T>
void LRUCache<K, T>::add(const K& key, const T& value) {
    assertSizeCorrect();
    assertKeyNotExist(key);
    if (links.size() == capacity) {
        assert(first != std::nullopt);
        erase(K(first.value()));
    }
    pushBack(key, value);
}

template<typename K, typename T>
void LRUCache<K, T>::erase(const K& key) {
    removeKey(key);
    values.erase(key);
}

template<typename K, typename T>
void LRUCache<K, T>::removeKey(const K& key) {
    assertSizeCorrect();
    assertKeyExist(key);

    assert(first != std::nullopt && last != std::nullopt);
    auto&[L, R] = links[key];
    if (first == key) {
        first = R;
    }
    if (last == key) {
        last = L;
    }
    if (R != std::nullopt) {
        links[R.value()].first = L;
    }
    if (L != std::nullopt) {
        links[L.value()].second = R;
    }
    links.erase(key);
    values.erase(key);

    assertFirstLastSynchronized();

    assertSizeCorrect();
    assertKeyNotExist(key);
}

template<typename K, typename T>
void LRUCache<K, T>::pushBack(const K& key, const T& value) {
    assertSizeCorrect();
    assertKeyNotExist(key);
    assertFirstLastSynchronized();
    if (last == std::nullopt) {
        links[key] = {std::nullopt, std::nullopt};
        first = key;
    } else {
        links[key] = {last, std::nullopt};
        links[last.value()].second = key;
    }
    last = key;
    values[key] = value;
    assertSizeCorrect();
    assertKeyExist(key);
    assert(last == key);
}


#endif //SD_TASK1_LRU_H
