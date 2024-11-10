document.addEventListener("DOMContentLoaded", function () {
    // Ensure modal is hidden initially
    const modal = document.getElementById("imageModal");
    modal.style.display = "none";
});

function openModal(img) {
    if (img.src) { // Check that image source is not null
        const modal = document.getElementById("imageModal");
        const modalImg = document.getElementById("modalImage");
        modal.style.display = "block";
        modalImg.src = img.src;
    }
}

function closeModal() {
    const modal = document.getElementById("imageModal");
    modal.style.display = "none";
    document.getElementById("modalImage").src = ""; // Clear the modal image source
}

// Close the modal if user clicks outside the image
window.onclick = function (event) {
    const modal = document.getElementById("imageModal");
    if (event.target === modal) {
        closeModal();
    }
};

function toggleAllDetails() {
    const detailsElements = document.querySelectorAll('details');
    const button = document.getElementById('toggleButton');

    if (button.textContent === 'Expand All') {
        detailsElements.forEach(detail => {
            if (!detail.open) detail.setAttribute('open', 'open');
        });
        button.textContent = 'Collapse All';
    } else {
        detailsElements.forEach(detail => {
            detail.removeAttribute('open');
        });
        button.textContent = 'Expand All';
    }
}
