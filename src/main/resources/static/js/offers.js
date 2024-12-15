document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('.favorite-form');

    forms.forEach(form => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            const actionUrl = form.getAttribute('action');
            const isAddFavorite = form.querySelector('button').textContent === "Ajouter aux favoris"; // Vérifier si c'est un ajout ou une suppression
            const method = isAddFavorite ? 'PUT' : 'DELETE'; // Utiliser PUT pour ajout et DELETE pour suppression

            try {
                const response = await fetch(actionUrl, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    // Vous pouvez recharger ou mettre à jour l'interface selon la réponse
                    location.reload();
                } else {
                    console.error('Erreur lors de l\'ajout/suppression aux favoris');
                    alert('Erreur lors de la mise à jour des favoris');
                }
            } catch (error) {
                console.error('Erreur lors de l\'ajout/suppression aux favoris:', error);
                alert('Impossible de mettre à jour les favoris. Veuillez réessayer.');
            }
        });
    });
});
