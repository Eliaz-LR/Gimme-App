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
            // Redirection en cas de succès
            window.location.href = "/";
        } else {
            // Gestion des erreurs
            const errorResponse = await response.json();
            displayErrorMessage(errorResponse.message || "Identifiants incorrects.");
        }
    } catch (error) {
        // Gestion des erreurs inattendues
        displayErrorMessage("Une erreur inattendue s'est produite. Veuillez réessayer.");
    }
});

function displayErrorMessage(message) {
    const errorDiv = document.getElementById("error-message");
    const errorText = document.getElementById("error-text");

    errorText.textContent = message; // Insère le message dans le paragraphe
    errorDiv.style.display = "block"; // Affiche le conteneur d'erreur
}
