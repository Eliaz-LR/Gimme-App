document.getElementById("login-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    const payload = {
        username: formData.get("username"),
        password: formData.get("password"),
    };

    try {
        const response = await fetch("/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(payload),
        });

        if (response.ok) {
            const responseData = await response.json();
            localStorage.setItem("jwt", responseData.token);

            window.location.href = "/";
        } else {
            const errorResponse = await response.json();
            displayErrorMessage(errorResponse.message);
        }
    } catch (error) {
        displayErrorMessage("Une erreur inattendue s'est produite. Veuillez réessayer.");
    }
});

function displayErrorMessage(message) {
    const errorDiv = document.getElementById("error-message");
    const errorText = document.getElementById("error-text");

    errorText.textContent = message;
    errorDiv.style.display = "block";
}
