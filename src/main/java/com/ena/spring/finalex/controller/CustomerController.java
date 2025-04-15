package com.ena.spring.finalex.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ena.spring.finalex.models.AirlineTicket;
import com.ena.spring.finalex.models.Customer;
import com.ena.spring.finalex.models.Reservation;
import com.ena.spring.finalex.repository.CustomerRepository;


@Controller
public class CustomerController {
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("reservation", new Customer());
	    return "reservationForm";
	}

    @GetMapping("/reservation")
    public String showForm(Model model) {
        model.addAttribute("reservation", new Customer());
        return "reservationForm";
    }

    @PostMapping("/reservation")
    public String submitForm(@ModelAttribute("reservation") Customer customer, RedirectAttributes redirectAttributes) {
        Customer saved = customerRepository.save(customer);
        return "redirect:/reservation/success/" + saved.getId();
    }
    
    @GetMapping("/reservation/success/{id}")
    public String showSuccess(@PathVariable String id, Model model) {
        Customer customer = customerRepository.findById(id).orElse(null);
        model.addAttribute("customer", customer);

        // Airport & seat class options
        List<String> airports = Arrays.asList("YYZ", "LAX", "JFK", "ATL", "LHR", "CDG", "DXB", "HND");
        List<String> seatClasses = Arrays.asList("Economy", "Business", "First Class");

        model.addAttribute("airports", airports);
        model.addAttribute("seatClasses", seatClasses);
        model.addAttribute("randomFlight", "FL-" + (int)(Math.random() * 10000));
        model.addAttribute("seatNumber", (int)(Math.random() * 100) + 1);

        return "reservationSuccess";
    }
    
    @GetMapping("/reservation/details/{id}")
    public String viewReservation(@PathVariable String id, Model model) {
        Customer customer = customerRepository.findById(id).orElse(null);
        model.addAttribute("customer", customer);
        return "reservationDetails";
    }
    
    @PostMapping("/reservation/cancel/{id}")
    public String cancelReservation(@PathVariable String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getReservation() != null) {
            customer.getReservation().setStatus("CANCELLED");
            customerRepository.save(customer);
        }
        return "redirect:/reservation/cancelled/" + id;
    }
    
    @GetMapping("/reservation/cancelled/{id}")
    public String cancelledPage(@PathVariable String id, Model model) {
        Customer customer = customerRepository.findById(id).orElse(null);
        model.addAttribute("customer", customer);
        return "reservationCancelled"; // goes to reservationCancelled.html
    }
    
    @PostMapping("/reservation/ticket/{id}")
    public String saveTicket(@PathVariable String id,
                             @RequestParam String departureAirport,
                             @RequestParam String arrivalAirport,
                             @RequestParam String seatClass,
                             @RequestParam String seatNumber,
                             @RequestParam String flightNumber) {

        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            AirlineTicket ticket = new AirlineTicket();
            ticket.setDepartureAirport(departureAirport);
            ticket.setArrivalAirport(arrivalAirport);
            ticket.setSeatClass(seatClass);
            ticket.setSeatNumber(seatNumber);
            ticket.setFlightNumber(flightNumber);

            Reservation reservation = new Reservation();
            reservation.setStatus("CONFIRMED");
            reservation.setReservationDate(LocalDateTime.now());
            reservation.setTicket(ticket);

            customer.setReservation(reservation);
            customerRepository.save(customer);
        }

        return "redirect:/reservation/details/" + id;
    }


}
