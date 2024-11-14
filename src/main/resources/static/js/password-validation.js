function checkPasswords() {
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirm-password");
    const submitButton = document.getElementById("submit-button");

    if (password.value !== confirmPassword.value) {
        confirmPassword.setCustomValidity("Les mots de passe ne correspondent pas.");
        password.classList.add("invalid");
        confirmPassword.classList.add("invalid");
        submitButton.disabled = true;
    } else {
        confirmPassword.setCustomValidity("");
        password.classList.remove("invalid");
        confirmPassword.classList.remove("invalid");
        submitButton.disabled = false;
    }
}

window.onload = function() {
    document.getElementById("password").addEventListener("input", checkPasswords);
    document.getElementById("confirm-password").addEventListener("input", checkPasswords);
};
