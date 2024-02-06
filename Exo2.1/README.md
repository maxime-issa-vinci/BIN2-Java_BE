| URI                     | Méthode HTTP | Auths? | Opération                                                                            |
|-------------------------|-----|-----|--------------------------------------------------------------------------------------|
| **pages**               | GET | JWT | READ ALL : Lire toutes les ressources de la collection                               |
| **pages/{id}**          | GET | JWT | READ ONE : Lire la ressource identifiée                                              |
| **pages**               | POST | JWT | CREATE ONE : Créer une ressource basée sur les données de la requête                 |
| **pages/{id}**          | PUT | JWT | UPDATE ONE : Replacer l'entièreté de la ressource par les données de la requête      |
| **pages/{id}**          | DELETE | JWT | DELETE ONE : Effacer la ressource identifiée                                         |
