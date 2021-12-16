-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 14 sep. 2020 à 20:39
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `e-commerce`
--

-- --------------------------------------------------------

--
-- Structure de la table `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `road` varchar(100) NOT NULL,
  `postal_code` varchar(30) NOT NULL,
  `city` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `updated_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `address`
--

INSERT INTO `address` (`id`, `id_user`, `road`, `postal_code`, `city`, `country`, `creation_date`, `updated_date`) VALUES
(8, 2, '63 boulevard de Stalingrad', '94400', 'Vitry', 'France', '2020-08-24 10:05:32', '2020-08-24 10:05:32'),
(15, 2, '15 rue test', '7500', 'Paris', 'France', '2020-08-24 16:23:19', '2020-08-24 16:23:19'),
(65, 2, '8 rue de la vie', '94200', 'Ivry', 'France', '2020-08-29 22:05:30', '2020-08-29 22:05:30'),
(69, 2, '15 rue test3', '75015', 'Paris', 'France', '2020-09-03 12:18:39', '2020-09-03 12:18:39'),
(70, 2, '15 rue test3', '75015', 'Paris', 'France', '2020-09-03 12:19:25', '2020-09-03 12:19:37'),
(72, 71, '7 rue maurice grandcoing', '94200', 'Ivry', 'France', '2020-09-03 12:21:43', '2020-09-03 12:21:43');

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE `commentaire` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `commentaire` text NOT NULL,
  `note` double NOT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `updated_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`id`, `id_user`, `id_produit`, `commentaire`, `note`, `creation_date`, `updated_date`) VALUES
(0, 2, 9, 'Correct', 8, '2020-08-24 12:09:34', '2020-08-24 12:09:34'),
(20, 2, 9, 'test', 2, '2020-08-24 17:46:56', '2020-08-24 17:46:56');

-- --------------------------------------------------------

--
-- Structure de la table `facturation`
--

CREATE TABLE `facturation` (
  `id` int(11) NOT NULL,
  `id_address` int(11) NOT NULL,
  `id_panier` int(11) NOT NULL,
  `livraison` varchar(100) NOT NULL DEFAULT 'EN_COURS',
  `payement` varchar(100) NOT NULL DEFAULT 'VALIDE',
  `total_prix` double NOT NULL,
  `total_qt` int(11) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `facturation`
--

INSERT INTO `facturation` (`id`, `id_address`, `id_panier`, `livraison`, `payement`, `total_prix`, `total_qt`, `creation_date`, `updated_date`) VALUES
(52, 15, 1, 'EN_COURS', 'VALIDE', 2, 2, '2020-08-24 20:58:45', '2020-08-24 20:58:45'),
(74, 70, 1, 'EN_COURS', 'VALIDE', 2, 2, '2020-09-03 12:29:34', '2020-09-03 12:29:34');

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(76),
(76),
(76),
(76),
(76),
(76);

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE `panier` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `updated_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id`, `id_user`, `id_produit`, `quantite`, `creation_date`, `updated_date`) VALUES
(1, 2, 12, 1, '2020-08-24 12:23:12', '2020-08-24 12:23:12'),
(40, 2, 13, 1, '2020-08-24 20:44:22', '2020-08-24 20:44:22'),
(42, 2, 13, 1, '2020-08-24 20:45:57', '2020-08-24 20:45:57'),
(43, 2, 13, 1, '2020-08-24 20:47:15', '2020-08-24 20:47:15'),
(44, 2, 13, 1, '2020-08-24 20:47:16', '2020-08-24 20:47:16'),
(45, 2, 13, 1, '2020-08-24 20:47:16', '2020-08-24 20:47:16'),
(46, 2, 13, 1, '2020-08-24 20:47:17', '2020-08-24 20:47:17'),
(47, 2, 13, 1, '2020-08-24 20:47:18', '2020-08-24 20:47:18'),
(48, 2, 13, 1, '2020-08-24 20:47:19', '2020-08-24 20:47:19'),
(49, 2, 13, 1, '2020-08-24 20:47:20', '2020-08-24 20:47:20'),
(53, 2, 13, 1, '2020-08-24 21:11:39', '2020-08-24 21:11:39');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `description` text NOT NULL,
  `prix` double NOT NULL,
  `reference` varchar(100) NOT NULL,
  `stock` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `updated_date` datetime DEFAULT current_timestamp(),
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `id_user`, `description`, `prix`, `reference`, `stock`, `nom`, `creation_date`, `updated_date`, `url`) VALUES
(9, 2, 'Appareil iOSd', 2500, '123456789fartou', 5, 'MacBook Pro', '2020-08-24 10:06:53', '2020-09-08 21:55:49', ''),
(12, 2, 'voiture puissante', 8001, '12345678', 6, 'Audi A3', '2020-08-24 10:15:53', '2020-09-08 21:56:23', ''),
(13, 2, 'Pour voir l\'heure', 50, '123456', 2, 'montre', '2020-08-24 15:22:45', '2020-09-02 22:12:29', 'https://www.gustave-et-cie.fr/wp-content/uploads/2019/09/PACKSHOT-MONTRES-24H-ANDRE-GUSTAVE-et-cie-bleu-or-cuir-marron.jpg'),
(67, 2, 'ghf', 45, 'fsdfgfh', 4, 'hftg', '2020-09-03 09:18:51', '2020-09-03 09:19:03', ''),
(68, 2, 'est', 50, 'd', -1, 'test', '2020-09-03 12:16:24', '2020-09-03 12:16:24', 'e'),
(73, 71, 'Utile pour les utilisateurs de IOS et swift', 2499, 'p123456789', 6, 'MacBook Pro 2', '2020-09-03 12:22:36', '2020-09-03 12:23:45', 'https://cdn.pocket-lint.com/r/s/1200x/assets/images/152137-laptops-review-apple-macbook-pro-2020-review-image1-pbzm4ejvvs.jpg'),
(75, 2, 'f', 50, '1234', 5, 'f', '2020-09-08 21:56:39', '2020-09-08 21:56:39', 'f');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT 'ROLE_USER',
  `creation_date` datetime DEFAULT current_timestamp(),
  `updated_date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role`, `creation_date`, `updated_date`) VALUES
(1, 'user', '$2a$10$xmxBvpIAASgRiD74eL3QDuhBDr5aOHosbklbgFQsDi/.s1K6./g7O', 'ROLE_USER', '2020-08-14 16:26:49', '2020-08-14 16:26:49'),
(2, 'herve', '$2a$10$AcW.syyYUi4y0rk04SC60uQB5xDdu7G.qgsd3eqWUYeF8Nv8SlcOq', 'ROLE_ADMIN', '2020-08-15 17:32:56', '2020-08-15 17:32:56'),
(3, 'fartouni', '$2a$10$6J2N5t5lZBEP0q77Nx8BEejzS.36PrwaQemvjTwR.3U55kCB1wy0O', 'ROLE_USER', '2020-08-15 17:33:29', '2020-08-15 17:33:29'),
(4, 'test', '$2a$10$0UAzhZbala4bw2LLv5qj3OPgM/rDuwDyLErN60tLAlxTRLV9S/pFq', 'ROLE_USER', '2020-08-15 17:33:53', '2020-08-15 17:33:53'),
(5, 'goku', '$2a$10$tSZsicuOVhGhrULCfClwreXUqxL6s1Jy.tUehbhyy5c8R6GiAOMWK', 'ROLE_USER', '2020-08-16 22:17:16', '2020-08-16 22:17:16'),
(7, 'wesh', '$2a$10$auFXv3d43roASoL.iIw1EuCOuipYupqbUtf.uvt/zEINnAt1GiT1C', 'ROLE_USER', '2020-08-17 00:23:21', '2020-08-17 00:23:21'),
(71, 'etnaadmin', '$2a$10$6IoDn9kEAAVYCosQeljDwuGmhmbfzsslc/.QtnbeY4m6QC1JNtFxa', 'ROLE_ADMIN', '2020-09-03 12:20:49', '2020-09-03 12:20:49');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `facturation`
--
ALTER TABLE `facturation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_address` (`id_address`),
  ADD KEY `id_panier` (`id_panier`);

--
-- Index pour la table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `commentaire_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `commentaire_ibfk_2` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`);

--
-- Contraintes pour la table `facturation`
--
ALTER TABLE `facturation`
  ADD CONSTRAINT `facturation_ibfk_1` FOREIGN KEY (`id_address`) REFERENCES `address` (`id`),
  ADD CONSTRAINT `facturation_ibfk_2` FOREIGN KEY (`id_panier`) REFERENCES `panier` (`id`);

--
-- Contraintes pour la table `panier`
--
ALTER TABLE `panier`
  ADD CONSTRAINT `panier_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `panier_ibfk_2` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FKre72i7h3dshla8vqfedqjvnhv` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
