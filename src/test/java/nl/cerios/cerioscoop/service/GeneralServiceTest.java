package nl.cerios.cerioscoop.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import nl.cerios.cerioscoop.domain.Category;
import nl.cerios.cerioscoop.domain.Customer;
import nl.cerios.cerioscoop.domain.Employee;
import nl.cerios.cerioscoop.domain.Movie;
import nl.cerios.cerioscoop.domain.MovieBuilder;
import nl.cerios.cerioscoop.domain.Show;
import nl.cerios.cerioscoop.domain.ShowPresentation;
import nl.cerios.cerioscoop.domain.ShowPresentationBuilder;
import nl.cerios.cerioscoop.util.DateUtils;
import nl.cerios.testutil.DatabaseTest;

public class GeneralServiceTest extends DatabaseTest {

	@InjectMocks
	private GeneralService generalService;
	private final DateUtils dateUtils = new DateUtils();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetMovies() {
		final List<Movie> movies = generalService.getMovies();

		Assert.assertNotNull(movies);
		Assert.assertEquals(3, movies.size());
	}
	
	@Test
	public void testGetShows() {
		final List<Show> shows = generalService.getShows();

		Assert.assertNotNull(shows);
		Assert.assertEquals(4, shows.size());
	}
		
	@Test
	public void testGetCustomers() {
		final List<Customer> customers = generalService.getCustomers();

		Assert.assertNotNull(customers);
		Assert.assertEquals(3, customers.size());
	}
	
	@Test
	public void testGetEmployees() {
		final List<Employee> employees = generalService.getEmployees();

		Assert.assertNotNull(employees);
		Assert.assertEquals(1, employees.size());
	}
	
	@Test
	public void testGetShowings() {
		final List<ShowPresentation> showings = generalService.getShowings();

		Assert.assertNotNull(showings);
		Assert.assertEquals(4, showings.size());
	}
	
	
	@Test
	public void testGetFirstShowAfterCurrentDate() throws ParseException{
	//Shows	
		final ShowPresentation showOne = new ShowPresentationBuilder()
				.withShowingId(BigInteger.valueOf(1))
				.withMovieTitle("showOne")
				.withRoomName("Yellow room")
				.withShowingDate(dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("08-01-2020"))))
				.withShowingTime(dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))))
				.build();	
		
		final ShowPresentation showTwo = new ShowPresentationBuilder()
				.withShowingId(BigInteger.valueOf(2))
				.withMovieTitle("showTwo")
				.withRoomName("Yellow room")
				.withShowingDate(dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("07-23-2020"))))
				.withShowingTime(dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))))
				.build();	
				
		final ShowPresentation showThree = new ShowPresentationBuilder()
				.withShowingId(BigInteger.valueOf(3))
				.withMovieTitle("showThree")
				.withRoomName("Yellow room")
				.withShowingDate(dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-03-2020"))))
				.withShowingTime(dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))))
				.build();	
		
		
	//Putting all movies in a list
		final List<ShowPresentation> listOfShows = new ArrayList<>();
		listOfShows.add(0, showOne);
		listOfShows.add(1, showTwo);
		listOfShows.add(2, showThree);
		
	//First show after the current date control 
		Assert.assertNotEquals(showOne.getShowingDate() ,generalService.getFirstShowAfterCurrentDate(listOfShows).getShowingDate());
		Assert.assertEquals(showTwo.getShowingDate() ,generalService.getFirstShowAfterCurrentDate(listOfShows).getShowingDate());
		Assert.assertNotEquals(showThree.getShowingDate() ,generalService.getFirstShowAfterCurrentDate(listOfShows).getShowingDate());
	}
	@Test
	public void testGetMovieByMovieId() throws MovieNotFoundException{
	//Movies	
		final Movie movieOne = new MovieBuilder()
				.withMovieId(BigInteger.valueOf(1))
				.withTitle("top titel")
				.withMinutes(98)
				.withType(3) // 3D
				.withLanguage("Fries")
				.withDescription("bagger v-film")
				.withCategory(Category.COMEDY)
				.build();
		final Movie movieTwo = new MovieBuilder()
				.withMovieId(BigInteger.valueOf(2))
				.withTitle("lekkere titel")
				.withMinutes(453)
				.withType(3) // 3D
				.withLanguage("Grieks")
				.withDescription("bagger v-film")
				.withCategory(Category.COMEDY)
				.build();
		final Movie movieThree = new MovieBuilder()
				.withMovieId(BigInteger.valueOf(3))
				.withTitle("keke titel")
				.withMinutes(9)
				.withType(3) // 3D
				.withLanguage("Twents")
				.withDescription("bagger v-film")
				.withCategory(Category.COMEDY)
				.build();
		
	//Putting all movies in a list
		final List<Movie> listOfMovies = new ArrayList<>();
		listOfMovies.add(0, movieOne);
		listOfMovies.add(1, movieTwo);
		listOfMovies.add(2, movieThree);
		
	//Movie control 
			Assert.assertEquals(movieOne ,generalService.getMovieByMovieId(movieOne.getMovieId().intValue(), listOfMovies));
			Assert.assertEquals(movieTwo ,generalService.getMovieByMovieId(movieTwo.getMovieId().intValue(), listOfMovies));
			Assert.assertEquals(movieThree ,generalService.getMovieByMovieId(movieThree.getMovieId().intValue(), listOfMovies));
	}
	
	@Test
	public void testRegisterCustomer() throws ParseException {
		final int idOfCustomerToBeRegistered = 4;
		final Customer customerOne = new Customer(idOfCustomerToBeRegistered, "Michael", "Boogerd", "MB", "MB123", "michael@boogerd.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("15-09-2017"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
		final Customer customerBefore = getCustomer(idOfCustomerToBeRegistered);
		Assert.assertNull(customerBefore);
		
		generalService.registerCustomer(customerOne);

		final Customer customerAfter = getCustomer(idOfCustomerToBeRegistered);
		Assert.assertNotNull(customerAfter);
		Assert.assertEquals(customerOne.getFirstName(), customerAfter.getFirstName());
		Assert.assertEquals(customerOne.getLastName(), customerAfter.getLastName());
		Assert.assertEquals(customerOne.getUsername(), customerAfter.getUsername());
		Assert.assertEquals(customerOne.getPassword(), customerAfter.getPassword());
		Assert.assertEquals(customerOne.getEmail(), customerAfter.getEmail());
		Assert.assertEquals(customerOne.getCreateDate(), customerAfter.getCreateDate());
		// Compare java.sql.Time objects by their String values, to prevent differences in milliseconds from failing the test.
		Assert.assertEquals(customerOne.getCreateTime().toString(), customerAfter.getCreateTime().toString());
	}
	
	@Test
	public void testAuthenticateCustomer() throws ParseException {
	//Customers
		final Customer customerOne = new Customer(0, "Bauke", "Mollema", "BM", "BM123", "bauke@mollema.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
		final Customer customerTwo = new Customer(1, "Tom", "Dumoulin", "TD", "TD123", "tom@dumoulin.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
		final Customer customerThree = new Customer(2, "Stef", "Clement", "SC", "SC123", "stef@clement.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
	
	//The no-customer test-user
		final Customer noCustomer = new Customer(3, "Chris", "Froome", "CF", "CF123", "chris@froome.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
	//Putting all customers in a list
		final List<Customer> dbCustomers = new ArrayList<>();
		dbCustomers.add(0, customerOne);
		dbCustomers.add(1, customerTwo);
		dbCustomers.add(2, customerThree);
		
	//Authentication control 
		Assert.assertEquals(customerOne ,generalService.authenticateCustomer(customerOne, dbCustomers));
		Assert.assertEquals(customerTwo ,generalService.authenticateCustomer(customerTwo, dbCustomers));
		Assert.assertEquals(customerThree ,generalService.authenticateCustomer(customerThree, dbCustomers));
		Assert.assertNotEquals(noCustomer ,generalService.authenticateCustomer(noCustomer, dbCustomers));
	}
	
	@Test
	public void testAuthenticateEmployee() throws ParseException {
	//Employee
		final Employee employeeOne = new Employee(0, "Wout", "Poels", "WP", "WP123", "wout@poels.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
		
		final Employee employeeTwo = new Employee(1, "Wilco", "Kelderman", "WK", "WK123", "wilco@kelderman.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
		
		final Employee employeeThree = new Employee(2, "Laurens", "tenDam", "LTD", "LTD123", "laurens@tendam.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
	//The no-employee test-user
		final Employee noEmployee = new Employee(3, "Tom", "Slagter", "TS", "TS123", "tom@slagter.com",
				dateUtils.convertUtilDateToSqlDate(dateUtils.toDate(dateUtils.toDateFormat("09-06-2016"))),
				dateUtils.convertUtilDateToSqlTime(dateUtils.toTime(dateUtils.toTimeFormat("20:00:00"))));
		
	//Putting all customers in a list
		final List<Employee> dbEmployees = new ArrayList<>();
		dbEmployees.add(0, employeeOne);
		dbEmployees.add(1, employeeTwo);
		dbEmployees.add(2, employeeThree);
		
	//Authentication control 
		Assert.assertEquals(employeeOne ,generalService.authenticateEmployee(employeeOne, dbEmployees));
		Assert.assertEquals(employeeTwo ,generalService.authenticateEmployee(employeeTwo, dbEmployees));
		Assert.assertEquals(employeeThree ,generalService.authenticateEmployee(employeeThree, dbEmployees));
		Assert.assertNotEquals(noEmployee ,generalService.authenticateEmployee(noEmployee, dbEmployees));
	}
	
	@Test
	public void testAuthenticateUser() {
		final Employee testUser = null;
		Assert.assertEquals(false, generalService.authenticateUser(testUser));
		final Customer testCustomer = new Customer(1, "Marcel", "Groothuis", "Manollo7G", "secret", "mjg@cerios.nl", dateUtils.getCurrentSqlDate(), dateUtils.getCurrentSqlTime());
		Assert.assertEquals(true, generalService.authenticateUser(testCustomer));
	}
	
	private Customer getCustomer(final int customerID) {
		return generalService.getCustomers().stream()
				.filter(c -> c.getCustomerId() == customerID)
				.findAny()
				.orElse(null);
	}
}
