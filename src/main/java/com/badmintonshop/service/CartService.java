package com.badmintonshop.service;

import com.badmintonshop.entity.*;
import com.badmintonshop.exception.ResourceNotFoundException;
import com.badmintonshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final StringingServiceRepository stringingServiceRepository;
    private final StringProductRepository stringProductRepository;
    private final UserRepository userRepository;

    public Cart getCart(User user, String sessionId) {
        if (user != null) {
            return cartRepository.findByUser(user)
                    .orElseGet(() -> createCart(user, null));
        } else {
            return cartRepository.findBySessionId(sessionId)
                    .orElseGet(() -> createCart(null, sessionId));
        }
    }

    private Cart createCart(User user, String sessionId) {
        Cart cart = Cart.builder()
                .user(user)
                .sessionId(sessionId)
                .build();
        return cartRepository.save(cart);
    }

    public Cart addToCart(User user, String sessionId, Long productId, Long variantId, int quantity) {
        Cart cart = getCart(user, sessionId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        ProductVariant variant = null;
        if (variantId != null) {
            variant = productVariantRepository.findById(variantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        }

        // Check if item exists
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId) &&
                        (variantId == null || item.getVariant().getVariantId().equals(variantId)))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            BigDecimal price = (variant != null) ? variant.getFinalPrice() : product.getBasePrice();
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .variant(variant)
                    .quantity(quantity)
                    .priceAtAdd(price)
                    .build();
            cart.addItem(newItem);
            cartItemRepository.save(newItem);
        }

        return cartRepository.save(cart);
    }

    public void updateStringingOption(Long cartItemId, Long serviceId, Long stringId, BigDecimal tension,
            String notes) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (serviceId != null) {
            StringingService service = stringingServiceRepository.findById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
            item.setStringingService(service);
        } else {
            item.setStringingService(null);
        }

        if (stringId != null) {
            StringProduct stringProduct = stringProductRepository.findById(stringId)
                    .orElseThrow(() -> new ResourceNotFoundException("String product not found"));
            item.setStringProduct(stringProduct);
        } else {
            item.setStringProduct(null);
        }

        item.setTension(tension);
        item.setStringingNotes(notes);

        cartItemRepository.save(item);
    }

    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart(User user, String sessionId) {
        Cart cart = getCart(user, sessionId);
        cart.clear();
        cartRepository.save(cart);
    }
}
