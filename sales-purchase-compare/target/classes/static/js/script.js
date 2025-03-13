// JavaScript for Sales and Purchase Comparison Application

// Wait for the document to be ready
$(document).ready(function() {
    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        $('.alert').alert('close');
    }, 5000);
    
    // Handle mark notification as read
    $('.mark-read-btn').click(function() {
        const id = $(this).data('id');
        $.get('/notification/read/' + id, function() {
            // Refresh notification count
            updateNotificationCount();
        });
    });
    
    // Handle delete notification
    $('.delete-btn').click(function() {
        if (confirm('Are you sure you want to delete this notification?')) {
            const id = $(this).data('id');
            $.ajax({
                url: '/notification/' + id,
                type: 'DELETE',
                success: function() {
                    // Reload page on successful deletion
                    location.reload();
                }
            });
        }
    });
    
    // Calculate price per unit in sale form
    $('#quantity, #subtotal').on('input', function() {
        const quantity = parseFloat($('#quantity').val()) || 0;
        const subtotal = parseFloat($('#subtotal').val()) || 0;
        
        if (quantity > 0) {
            const pricePerUnit = (subtotal / quantity).toFixed(2);
            $('#pricePerUnit').val(pricePerUnit);
        }
    });
    
    // Function to update notification count
    function updateNotificationCount() {
        $.get('/notification/count', function(count) {
            const badge = $('.notification-badge');
            if (count > 0) {
                badge.text(count).show();
            } else {
                badge.hide();
            }
        });
    }
    
    // Periodically update notification count (every 30 seconds)
    setInterval(updateNotificationCount, 30000);
});