document.addEventListener("DOMContentLoaded", function () {
    const saveSearchButton = document.getElementById("saved-search");

    if (saveSearchButton) {
        saveSearchButton.addEventListener('click', async () => {
            const urlParams = new URLSearchParams(window.location.search);
            const searchText = urlParams.get('search');
            console.log("searchText:", searchText);  // Log pour vérifier si searchText est récupéré

            if (!searchText) {
                alert("Aucun texte de recherche trouvé.");
                return;
            }

            const actionUrl = '/searches';  // URL sans paramètre
            console.log("actionUrl:", actionUrl);  // Log pour vérifier l'URL

            try {
                const response = await fetch(actionUrl, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        search: searchText  // Envoie le texte de recherche dans le corps de la requête
                    }),
                });

                console.log("response:", response);  // Log pour vérifier la réponse du serveur
                if (response.ok) {
                    const successDiv = document.getElementById("success-message");
                    const successText = document.getElementById("success-text");
                    successText.textContent = "Recherche sauvegardé !"
                    successDiv.style.display = "block";
                } else {
                    alert("Erreur lors de la sauvegarde de la recherche.");
                }
            } catch (error) {
                console.error('Erreur lors de la soumission du formulaire:', error);
                alert('Une erreur est survenue. Veuillez réessayer.');
            }
        });
    } else {
        console.error("Le bouton n'a pas été trouvé.");
    }
});
