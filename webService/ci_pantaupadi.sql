-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 06, 2019 at 03:20 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 5.6.36

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ci_pantaupadi`
--

-- --------------------------------------------------------

--
-- Table structure for table `country`
--

CREATE TABLE `country` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `country`
--

INSERT INTO `country` (`id`, `name`, `status`) VALUES
(1, 'Afghanistan', 1),
(2, 'Albania', 1),
(3, 'Algeria', 1),
(4, 'Andorra', 1),
(5, 'Angola', 1),
(6, 'Antigua and Barbuda', 1),
(7, 'Argentina', 1),
(8, 'Armenia', 1),
(9, 'Australia', 1),
(10, 'Austria', 1),
(11, 'Azerbaijan', 1),
(12, 'Bahamas', 1),
(13, 'Bahrain', 1),
(14, 'Bangladesh', 1),
(15, 'Barbados', 1),
(16, 'Belarus', 1),
(17, 'Belgium', 1),
(18, 'Belize', 1),
(19, 'Benin', 1),
(20, 'Bhutan', 1),
(21, 'Bolivia', 1),
(22, 'Bosnia and Herzegovina', 1),
(23, 'Botswana', 1),
(24, 'Brazil', 1),
(25, 'Brunei', 1),
(26, 'Bulgaria', 1),
(27, 'Burkina Faso', 1),
(28, 'Burundi', 1),
(29, 'Cabo Verde', 1),
(30, 'Cambodia', 1),
(31, 'Cameroon', 1),
(32, 'Canada', 1),
(33, 'Central African Republic', 1),
(34, 'Chad', 1),
(35, 'Chile', 1),
(36, 'China', 1),
(37, 'Colombia', 1),
(38, 'Comoros', 1),
(39, 'Congo, Republic of the', 1),
(40, 'Congo, Democratic Republic of the', 1),
(41, 'Costa Rica', 1),
(42, 'Cote d Ivoire', 1),
(43, 'Croatia', 1),
(44, 'Cuba', 1),
(45, 'Cyprus', 1),
(46, 'Czech Republic', 1),
(47, 'Denmark', 1),
(48, 'Djibouti', 1),
(49, 'Dominica', 1),
(50, 'Dominican Republic', 1),
(51, 'Ecuador', 1),
(52, 'Egypt', 1),
(53, 'El Salvador', 1),
(54, 'Equatorial Guinea', 1),
(55, 'Eritrea', 1),
(56, 'Estonia', 1),
(57, 'Ethiopia', 1),
(58, 'Fiji', 1),
(59, 'Finland', 1),
(60, 'France', 1),
(61, 'Gabon', 1),
(62, 'Gambia', 1),
(63, 'Georgia', 1),
(64, 'Germany', 1),
(65, 'Ghana', 1),
(66, 'Greece', 1),
(67, 'Grenada', 1),
(68, 'Guatemala', 1),
(69, 'Guinea', 1),
(70, 'Guinea-Bissau', 1),
(71, 'Guyana', 1),
(72, 'Haiti', 1),
(73, 'Honduras', 1),
(74, 'Hungary', 1),
(75, 'Iceland', 1),
(76, 'India', 1),
(77, 'Indonesia', 1),
(78, 'Iran', 1),
(79, 'Iraq', 1),
(80, 'Ireland', 1),
(81, 'Italy', 1),
(82, 'Jamaica', 1),
(83, 'Japan', 1),
(84, 'Jordan', 1),
(85, 'Kazakhstan', 1),
(86, 'Kenya', 1),
(87, 'Kiribati', 1),
(88, 'Kosovo', 1),
(89, 'Kuwait', 1),
(90, 'Kyrgyzstan', 1),
(91, 'Laos', 1),
(92, 'Latvia', 1),
(93, 'Lebanon', 1),
(94, 'Lesotho', 1),
(95, 'Liberia', 1),
(96, 'Libya', 1),
(97, 'Liechtenstein', 1),
(98, 'Lithuania', 1),
(99, 'Luxembourg', 1),
(100, 'Macedonia', 1),
(101, 'Madagascar', 1),
(102, 'Malawi', 1),
(103, 'Malaysia', 1),
(104, 'Maldives', 1),
(105, 'Mali', 1),
(106, 'Malta', 1),
(107, 'Marshall Islands', 1),
(108, 'Mauritania', 1),
(109, 'Mauritius', 1),
(110, 'Mexico', 1),
(111, 'Micronesia', 1),
(112, 'Moldova', 1),
(113, 'Monaco', 1),
(114, 'Mongolia', 1),
(115, 'Montenegro', 1),
(116, 'Morocco', 1),
(117, 'Mozambique', 1),
(118, 'Myanmar (Burma)', 1),
(119, 'Namibia', 1),
(120, 'Nauru', 1),
(121, 'Nepal', 1),
(122, 'Netherlands', 1),
(123, 'New Zealand', 1),
(124, 'Nicaragua', 1),
(125, 'Niger', 1),
(126, 'Nigeria', 1),
(127, 'North Korea', 1),
(128, 'Norway', 1),
(129, 'Oman', 1),
(130, 'Pakistan', 1),
(131, 'Palau', 1),
(132, 'Palestine', 1),
(133, 'Panama', 1),
(134, 'Papua New Guinea', 1),
(135, 'Paraguay', 1),
(136, 'Peru', 1),
(137, 'Philippines', 1),
(138, 'Poland', 1),
(139, 'Portugal', 1),
(140, 'Qatar', 1),
(141, 'Romania', 1),
(142, 'Russia', 1),
(143, 'Rwanda', 1),
(144, 'St. Kitts and Nevis', 1),
(145, 'St. Lucia', 1),
(146, 'St. Vincent and The Grenadines', 1),
(147, 'Samoa', 1),
(148, 'San Marino', 1),
(149, 'Sao Tome and Principe', 1),
(150, 'Saudi Arabia', 1),
(151, 'Senegal', 1),
(152, 'Serbia', 1),
(153, 'Seychelles', 1),
(154, 'Sierra Leone', 1),
(155, 'Singapore', 1),
(156, 'Slovakia', 1),
(157, 'Slovenia', 1),
(158, 'Solomon Islands', 1),
(159, 'Somalia', 1),
(160, 'South Africa', 1),
(161, 'South Korea', 1),
(162, 'South Sudan', 1),
(163, 'Spain', 1),
(164, 'Sri Lanka', 1),
(165, 'Sudan', 1),
(166, 'Suriname', 1),
(167, 'Swaziland', 1),
(168, 'Sweden', 1),
(169, 'Switzerland', 1),
(170, 'Syria', 1),
(171, 'Taiwan', 1),
(172, 'Tajikistan', 1),
(173, 'Tanzania', 1),
(174, 'Thailand', 1),
(175, 'Timor-Leste', 1),
(176, 'Togo', 1),
(177, 'Tonga', 1),
(178, 'Trinidad and Tobago', 1),
(179, 'Tunisia', 1),
(180, 'Turkey', 1),
(181, 'Turkmenistan', 1),
(182, 'Tuvalu', 1),
(183, 'Uganda', 1),
(184, 'Ukraine', 1),
(185, 'United Arab Emirates', 1),
(186, 'United Kingdom (UK)', 1),
(187, 'United States of America (USA)', 1),
(188, 'Uruguay', 1),
(189, 'Uzbekistan', 1),
(190, 'Vanuatu', 1),
(191, 'Vatican City (Holy See)', 1),
(192, 'Venezuela', 1),
(193, 'Vietnam', 1),
(194, 'Yemen', 1),
(195, 'Zambia', 1),
(196, 'Zimbabwe', 1);

-- --------------------------------------------------------

--
-- Table structure for table `daun`
--

CREATE TABLE `daun` (
  `id` int(5) NOT NULL,
  `user_id` int(5) NOT NULL,
  `nama_penyakit` varchar(50) NOT NULL,
  `solusi` text NOT NULL,
  `gambar` varchar(100) NOT NULL,
  `kondisi` text NOT NULL,
  `penulis` varchar(50) NOT NULL,
  `tanggal_upload` date NOT NULL,
  `value_warna` int(15) NOT NULL,
  `usia` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `daun`
--

INSERT INTO `daun` (`id`, `user_id`, `nama_penyakit`, `solusi`, `gambar`, `kondisi`, `penulis`, `tanggal_upload`, `value_warna`, `usia`) VALUES
(27, 0, 'Kahat Kalium', 'Cara aplikasi atau cara pemberian pupuk KCl pada tanaman padi dilakukan dengan cara ditaburkan dengan dosis dan waktu/jadwal pemupukan sesuai dengan rekomendasi setempat. Atau berdasarkan kadar kalium pada lahan yang digunakan. Pada lahan sawah dengan kadar kalium rendah, dosis pupuk KCl untuk tanaman padi adalah 100 kg/ha, sedangkan pada lahan dengan kadar kalium sedang sampai tinggi dosis pupuk KCl untuk tanaman padi adalah 50 kg/ha. Pupuk KCl diberikan dua kali, yaitu pada umur 21 HST dan 42 HST.', 'Kahat_Kalium.jpg', 'warna menjadi kekuningan/oranye sampai kecoklatan yang dimulai dari ujung daun terus menjalar ke pangkal daun, anakn berkurang, ukuran dan berat gabah berkurang. ', 'Admin', '2019-07-17', 0, 21),
(28, 0, 'Kahat Fosfor', 'Secara umum, P telah diidentifikasi sebagai unsur hara yang penting bagi kesehatan akar tanaman dan menambah ketahanan tanaman terhadap keracunan besi. Ekstraksi tanah menggunakan larutan HCI 25% adalah cara yang paling tepat untuk menetapkan status hara P tanah. kemudian ditetapkan dosis rekomendasi pernupukan P, yaitu 100-125 kg SP36/ha tiap musim, 75 kg SP36/ha tiap dua musim, dan 50 kg SP36/ha tiap empat musim masingmasing untuk tanah berstatus P rendah, sedang, dan tinggi', 'Kahat_Fosfor.jpg', 'Gejala kekurangan fosfor menyebabkan pertumbuhan akar tanaman lambat, tanaman kerdil, daun berwarna keungu-unguan, anakan sedikit, waktu pembungaan terlambat atau tidak rata, umur tanaman/panen lebih panjang, dan gabar yang terbentuk berkurang', 'Admin', '2019-07-17', 1, 20),
(29, 0, 'Kahat Belerang', 'Masukkan jerami alih-alih membuang atau membakarnya. Meningkatkan manajemen tanah untuk meningkatkan serapan Sulfur dengan mempertahankan perkolasi yang cukup (~ 5mm per hari) atau dengan melakukan pengolahan tanah kering setelah panen.', 'Kahat_Belerang.jpg', 'Gejala kekurangan belerang adalah berupa klorosis pada daun-daun muda, diikuti dengan menguningnya daun tua dan seluruh tanaman, pertumbuhan kerdil, jumlah anakan dan malai berkurang. Kekurangan belerang umumnya terjadi pada tanah yang kadungan bahan organiknya rendah, tanah reduktif, dan atau pH tinggi. Unsur S sebenarnya banyak hilang akibat pembakaran sisa-sisa tanaman', 'Admin', '2019-07-17', 2, 0),
(30, 0, 'Kahat Seng', 'Tanaman akan segera sembuh dari gejala kekurangan unsur hara Zn setelah sawah dikeringkan. Jika gejala kekurangan Zn ringan, cukup diberikan 5 kg Zn/ha (ZnSO4), dan bila gejalanya berat diberika 20 kg Zn/ha.', 'Kahat_Seng.jpg', 'Daun tanaman padi yang kahat Zn hilang ketegaranya dan cenderung mengapung di atas air; setengah dari tajuk bagian bawah, daunya berwarna hijau pucat 2-4 hari setealh digenangi, kemudian khlorotik dan mulai mengering setelah 3-7 hari digenangi. Gejala khlorosis yang terberat umumnya terjadi pada saat air menggenang dalam. Gejala kekurangan Zn ini mirip dengan yang dikatakan \"asem-aseman\" oleh sebagian petani. ', 'Admin', '2019-07-17', 3, 0),
(31, 0, 'Kahat Nitrogen', 'Upaya yang harus dilakukan oleh petani, dalam rangka untuk mengatasi lahan kekurangan N, diantaranya adalah : segera melakukan pemupukan dengan Nitrogen dengan pemberian pupuk Kimia maupun organik , misalnya pupuk kimia yang mengandung unsur N urea, Za, maupun NPK, sedang bila menggunakan pupuk organik misanya dapat menggunakan pupuk kandang, pupuk kompos atau pupuk hijau ( misalnya orok-orok) atau menggunakan tanaman polong seperti kacang2an, turi, lamtoro dll. Yang perlu diperhatikan agar pemberian pupuk N dari pupk Kimia hendaknya faktor kelembaban tanah/air harus betul-betul diperhatikan agar pupuk yang ada tidak banyak yang hilang atau dapat terserap dengan baik, mengingat sifat pupuk N, mudah larut.', 'Kahat_Nitrogen.jpg', 'Tanaman yang mengalami kahat nitrogen memperlihatkan gejala pertumbuhan tanaman kerdil dan menguning, daun lebih kecil dibandingkan daun tanaman sehat. Gejala umum kekurangan N pada tanaman muda adalah seluruh tanaman menguning, sedangkan pada tanaman tua gejalanya terlihat nyata pada daun bagian bawah yang berwarna kekuning-kuningan hingga kuning. Selain itu, anakan yang dihasilkan berkurang dan terlambat berbunga, tetapi proses pemasakan lebih cepat sehingga kebernasan berkurang. Gabar dari maali yang dihasilkan juga berkurang', 'Admin', '2019-07-17', 4, 0),
(32, 0, 'Kerdil Hampa (Ragged Stunt)', '\"Karena ditularkan oleh wereng coklat, maka pengendalian yang tepat adalah dengan mengendalikan wereng coklat. Praktek penanaman disinkronkan. Bajak tunggul yang terinfeksi di bawah ladang setelah panen untuk mengurangi sumber virus. Menggunakan varietas tahan untuk manajemen kerdil yang kasar mungkin merupakan langkah kontrol yang paling penting. Hubungi kantor pertanian setempat untuk mengetahui daftar varietas terbaru yang tersedia.\"', 'Kerdil_Hampa_(Ragged_Stunt).jpg', 'Patogen penyebab penyakit kerdil hampa adalah virus yang ditularkan oleh wereng coklat. Tanaman yang terinfeksi menjadi kerdil. Gejala lainya bervariasi tergantung fase pertumbuhan tanaman. Tanaman sehat dan sakit mempunyai anakan yang sama pada awalnya, tanaman sakit tetap hijau pada fase pemasakan dan mempunyai lebih banyak anakan daripada tanaman sehat. Daun-daun bergerigi merupakan gejala awal yang jeals pada fase awal tanaman muda. Pinggir daun yang tidak rata atau pecah-pecah dapat terlihat sebelum daun menggulung. Bagian helai daun yang rusak menunjukkan gejala khlorotik, menjadi kuning atau kuning kecoklat-coklatan, dan terpecah-pecah. Serangan pada daun bendera menyebabkan daun melintir, berubah bentuk, dan memendek pada fase bunting', 'Admin', '2019-07-17', 5, 43),
(35, 100, 'BWD 2', 'tambahkan urea  75-100 kg per hektar', 'BWD_2.png', 'daun teratas yang telah membuka penuh pada satu rumpun berwarna hijau terang ', 'Admin', '2019-07-29', 8, 25),
(36, 100, 'BWD 3', 'tambahkan pupuk urea 50-75 kg per hektar', 'BWD_3.png', 'daun teratas yang telah membuka penuh pada satu rumpun berwarna hijau ', 'Admin', '2019-07-29', 9, 25),
(37, 100, 'BWD 4', 'tambahkan pupuk urea 50-75  kg per hektar', 'BWD_4.png', 'daun teratas yang telah membuka penuh pada satu rumpun berwarna hijau gelap', 'Admin', '2019-07-29', 0, 25),
(38, 100, 'BWD 5', 'tambahkan pupuk urea sebesar 50kg per hektar ', 'BWD_5.png', 'daun teratas yang telah membuka penuh pada satu rumpun berwarna hijau gelap', 'Admin', '2019-07-29', 10, 25);

-- --------------------------------------------------------

--
-- Table structure for table `daun_identifikasi`
--

CREATE TABLE `daun_identifikasi` (
  `id` int(5) NOT NULL,
  `image_get` blob NOT NULL,
  `kondisi` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `settings_id` int(11) NOT NULL,
  `type` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`settings_id`, `type`, `description`) VALUES
(1, 'system_name', 'Pantau Padi '),
(2, 'system_title', 'Pantau Padi');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `country` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `mobile`, `password`, `country`, `created_at`, `status`, `role`) VALUES
(100, 'titik', 'nurfaidah', 'nurfaidahtitik@gmail.com', '0923840398', '5f3f9d3455da16f1c321d865c31b030a', 77, '0000-00-00 00:00:00', 1, 'admin'),
(101, 'Iqlima', 'Nur Hayati', 'iqlima@mail.com', '085330477653', '9fb9de74bbc62e6f1549baa4d78cc22a', 16, '2019-07-30 07:54:30', 1, 'user');

-- --------------------------------------------------------

--
-- Table structure for table `user_power`
--

CREATE TABLE `user_power` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `power_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_power`
--

INSERT INTO `user_power` (`id`, `name`, `power_id`) VALUES
(2, 'edit', 2),
(3, 'delete', 3),
(4, 'add', 1),
(5, 'power', 4);

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`id`, `user_id`, `action`) VALUES
(1, 11, 1),
(2, 11, 3),
(3, 13, 2),
(4, 14, 1),
(5, 14, 3),
(6, 15, 1),
(7, 15, 3),
(11, 16, 1),
(12, 16, 2),
(13, 101, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `daun`
--
ALTER TABLE `daun`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `daun_identifikasi`
--
ALTER TABLE `daun_identifikasi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`settings_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_power`
--
ALTER TABLE `user_power`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `country`
--
ALTER TABLE `country`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=197;

--
-- AUTO_INCREMENT for table `daun`
--
ALTER TABLE `daun`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `daun_identifikasi`
--
ALTER TABLE `daun_identifikasi`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `settings_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT for table `user_power`
--
ALTER TABLE `user_power`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
