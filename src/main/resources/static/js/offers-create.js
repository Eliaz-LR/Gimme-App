document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".offer-form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(form);
        const offerData = {
            name: formData.get("name"),
            description: formData.get("description"),
            category: formData.get("category"),
            condition: formData.get("condition"),
            postcode: formData.get("postcode"),
            keywords: formData.get("keywords") ? formData.get("keywords").split(",") : [],
            canBeSentByPost: formData.has("canBeSentByPost")
        };

        console.log(JSON.stringify(offerData));

        try {
            const response = await fetch("/offers", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(offerData)
            });

            if (response.ok) {
                const successDiv = document.getElementById("success-message");
                const successText = document.getElementById("success-text");
                successText.textContent = "Annonce déposé avec succès"
                successDiv.style.display = "block";
            } else {
                const error = await response.json();
                alert("Erreur : " + (error.message || "Une erreur est survenue."));
            }
        } catch (error) {
            console.error("Erreur lors de l'envoi des données :", error);
            alert("Impossible de créer l'offre. Veuillez réessayer plus tard.");
        }
    });
});
