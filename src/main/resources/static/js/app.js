function toggleDropdown() {
    var dropdown = document.getElementById("userDropdownHome");
    dropdown.classList.toggle("hidden");
}

document.addEventListener('DOMContentLoaded', function() {
    const toggleButton = document.getElementById('userDropdownHomeToggle');
    if (toggleButton) {
        toggleButton.addEventListener('click', toggleDropdown);
    }
});