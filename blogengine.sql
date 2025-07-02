-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 05:59 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blogengine`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `report_blog` (IN `userid` VARCHAR(25), IN `authorid` VARCHAR(25), IN `title` VARCHAR(30))   BEGIN
INSERT INTO reported_blogs (User_id,Author_id,title,time) VALUES(userid,authorid,title,CURRENT_TIME);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateLog` (IN `userid` VARCHAR(25), IN `status` VARCHAR(30))   BEGIN
INSERT INTO logs (User_id,status,time) VALUES(userid,status,CURRENT_TIME);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `blogs`
--

CREATE TABLE `blogs` (
  `Author` varchar(18) NOT NULL,
  `title` varchar(35) NOT NULL,
  `blog` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `blogs`
--

INSERT INTO `blogs` (`Author`, `title`, `blog`) VALUES
('Joshua Gill', 'Putting a price on soils', 'Healthy soils do much more than produce food. They perform a multitude of vital functions, like filtering water, supporting biodiversity, and even protecting the planet from climate change. Just as the environmental costs of intensive farming never factor into the price of food, farmers who build soil health are rarely compensated for the ecosystem services they provide. As a result of these misaligned incentives, the hidden costs of the agrifood system are estimated to be around $10 trillion.\r\n\r\nGiven that the current system is no longer fit for purpose, it\'s now time to rethink our approach to farming. The agrifood system faces the uphill task of producing more food for a growing population on increasingly stressed and limited arable land, while adapting to inevitable climate change, and without adding to greenhouse emissions.\r\n\r\nWhile the problem seems intractable, the solutions – ranging from conservation agriculture to efficient pasture management and the appropriate use of organic and inorganic fertilizers – are available and can be affordable. For instance, soils could sequester about 1 billion tons of solid carbon per year cost-effectively.\r\n\r\n'),
('Leszek J. Sibilski', 'Cycling may be old', 'Bicycles were invented in the nineteenth century—the same century that gave us the typewriter, the telegraph, and the landline telephone. Unlike those inventions that fell by the wayside when more modern devices came along, the bicycle has stayed with us, essentially unchanged, for over 200 years. In fact, it has continued to win new fans, generation after generation.\r\n\r\nThe benefits of cycling are manifold. Not only does cycling cut transport emissions, but it also reduces the need for expensive roads and highways.  And, unlike most forms of motorized transport, the bicycle promotes physical activity, social engagement, and a sense of freedom. It also gives the rider immediate awareness of—and connection to—the local environment.\r\n\r\nCycling is also a great equalizer. Bicycles are typically inexpensive and almost infinitely repairable, and no jurisdiction in the world requires a bicyclist’s license or imposes any other meaningful barrier to entry.\r\n\r\nMy love of bicycles, and my strident belief in the role they can play in improving mobility worldwide, led me to advocate for the establishment of a special day to celebrate and promote one of the most reliable and loyal human tools—a true mainstay of human mobility. On April 12, 2018, I proudly witnessed all member states of the UN General Assembly adopt a resolution to declare June 3rd as World Bicycle Day.\r\n\r\nThis year, I was delighted to celebrate the 7th edition of World Bicycle Day at the World Bank in Washington, D.C. at a special event organized by staff who want to promote the uptake of cycling as part of their professional mission. \r\n\r\nReflecting on the evolution of World Bicycle Day over the years—and the recent celebration at the World Bank—has reinforced a key point: Since cycling is here to stay and has enormous potential to improve people’s lives, we need to step up our collective investment in cycling infrastructure, particularly in low and middle-income countries.'),
('Ella Jisun Kim', 'Heat and gender', 'Extreme heat is one of the deadliest natural hazards, with South Asia seeing over 110,000 heat-related excess deaths a year in the last two decades – a worrisome statistic as 2024 is shaping up to be increasingly likely to be the warmest year on record. While heat is often thought of as an indiscriminate killer, not everyone is impacted by heat equally. Women bear a disproportionate burden of heat’s physical, social, and financial effects as temperatures rise. Women are at higher risk for heat-related illness and have higher death rates than men during heat waves due to physiological differences, disparities in access to electricity and water, and social norms around women as caregivers.\r\n\r\nHeat has a profound impact on maternal and neonatal health, with research showing links between heat exposure and pre-term births, miscarriages, and stillbirths. Furthermore, heat often creates a “double burden” for women as they are not only more physically susceptible to its effects but also tend to be expected to care for other heat-vulnerable household members, including children and the elderly. A report by HomeNet South Asia, a regional network of home-based workers, found that 43 percent of women surveyed reported an increase in caregiving due to extreme heat. Lastly, women face bigger proportional income losses from extreme heat, with female-headed households losing 8 percent more of their annual incomes when compared to male-headed households in low and middle-income countries.\r\n\r\nThe heightened vulnerability of females necessitates gender-informed heat management solutions, particularly in the following areas:\r\n\r\nWomen tend to have less access to information on the risks of extreme heat. According to Bijal Brahmbhatt, CEO of Mahila Housing Trust, experiential learning can empower women on concepts like heat waves, indoor temperatures, and ventilation. Through their surveillance toolkits distributed to over 25,000 households in 100 informal settlements in India, Bangladesh, and Nepal, women are trained to log data from temperature and humidity sensors three times a day, enabling a better understanding of how heat is distributed in communities. Bushra Afreen, Chief Heat Officer of Dhaka, is developing heat awareness materials with UNICEF and the Bangladesh Red Crescent Society to catalyze public engagement with heat risks and solutions in communities across Dhaka.\r\n Women are falling through cracks in existing early warning systems. In her survey of over 13,000 households in Karachi, Nausheen Anwar, professor at IBA and founder of Karachi Urban Lab, found over 50 percent had never received a heat alert of any kind despite an emphasis on early warning systems in Karachi’s heat wave management plan. Similarly, as over 40 percent of Mahila Housing Trust’s female members do not own mobile phones, MHT has directly partnered with India Meteorological Department to disseminate weekly forecast data through its meetings, posters, and radio announcements.\r\nWomen have limited access to cooling spaces outside the home, while often cooking in dwellings that lack fans, running water, and toilets. According to Afreen, women in Dhaka with inadequate access to sanitation services reduce water consumption to avoid having to urinate, amplifying risks of dehydration on hot days, in line with research probing why women in informal settlements were most at risk in the 2010 Ahmedabad heat wave. Therefore, Afreen is spearheading an initiative to pilot women-friendly cooling zones in Dhaka with shade, water booths, and access to bathrooms.\r\nWomen need financial protection from extreme heat. Brahmbhatt at the Mahila Housing Trust is piloting a parametric insurance scheme to cover heat-related income losses for women working in India’s informal sector. Insurance is paid out according to pre-set temperature thresholds, allowing members the flexibility to either work more safely or stay home in dangerous temperatures.\r\nDespite being disproportionately affected by heat-related issues, women are underrepresented in positions of power that influence decision-making and policies on heat. When asked what their number one priority for alleviating the impacts of extreme heat in South Asia, Anwar answered that she would want “architects, engineers, urban planners, medical doctors, and epidemiologists to sit at the same table to have a profound conversation on what a new urban future in light of global warming trends would look like, with gender right at the heart of these conversations,” while Brahmbhatt similarly emphasized the importance of a “future where women can be part of the conversation in the public and private sectors and influence policies, governance, and budgets.”'),
('pro', 'pros', 'pro pro\n');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `Log_id` int(11) NOT NULL,
  `User_id` varchar(25) NOT NULL,
  `status` varchar(30) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`Log_id`, `User_id`, `status`, `time`) VALUES
(1, 'Kavya_29', 'Signup', '2024-08-23 13:08:26'),
(10, 'Kavya_29', 'Signup', '2024-08-23 18:24:32');

-- --------------------------------------------------------

--
-- Table structure for table `reported_blogs`
--

CREATE TABLE `reported_blogs` (
  `report_id` int(11) NOT NULL,
  `User_id` varchar(25) NOT NULL,
  `Author_id` varchar(25) NOT NULL,
  `title` varchar(25) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reported_blogs`
--

INSERT INTO `reported_blogs` (`report_id`, `User_id`, `Author_id`, `title`, `time`) VALUES
(2, 'Kavya_29', 'pro', 'pros', '2024-08-23 18:25:04');

-- --------------------------------------------------------

--
-- Table structure for table `user_details`
--

CREATE TABLE `user_details` (
  `User_id` varchar(18) NOT NULL,
  `First_name` varchar(20) NOT NULL,
  `Last_name` varchar(20) NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_details`
--

INSERT INTO `user_details` (`User_id`, `First_name`, `Last_name`, `Email`, `Password`) VALUES
('Kavya_29', 'patel', 'kavya', 'pkavya085@gmail.com', 'password');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`Log_id`);

--
-- Indexes for table `reported_blogs`
--
ALTER TABLE `reported_blogs`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `user_details`
--
ALTER TABLE `user_details`
  ADD PRIMARY KEY (`User_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `Log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `reported_blogs`
--
ALTER TABLE `reported_blogs`
  MODIFY `report_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
