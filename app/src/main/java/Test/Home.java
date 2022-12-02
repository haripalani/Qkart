package Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    ChromeDriver driver;
    String url ="https://crio-qkart-frontend-qa.vercel.app/";

    public Home(ChromeDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.xpath("//button[text()='Logout']"));
            logout_button.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("css-1urhf6j"), "Logout"));

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    public boolean searchForProduct(String product) throws InterruptedException {
        try {
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searchBox = driver.findElement(By.xpath("//input[@name='search'][1]"));
            searchBox.clear();
            searchBox.sendKeys(product);
            Thread.sleep(5000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElementLocated(By.className("css-yg30e6"), product),
            ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='MuiCardContent-root css-1qw96cp'|//h4]"))));
            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {};
        try {
          
            searchResults = driver.findElements(By.className("css-1qw96cp"));
            return searchResults;}
            catch (Exception e) {
                System.out.println("There were no search results: " + e.getMessage());
                return searchResults;}
    }

    public boolean isNoResultFound() {
        boolean status = false;
        try {
            status = driver.findElement(By.xpath("//h4")).isDisplayed();
            return status;
        } catch (Exception e) {
            //handle exception
        }
        return status;
    }

    public void addProductToCart(String string) {
        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add to cart']"));
        addButton.click();
    }

    public void clickCheckout() {
        WebElement Checkout = driver.findElement(By.xpath("//button[text()='Checkout']"));
        Checkout.click();
    }


        public Boolean changeProductQuantityinCart(String productName, int quantity) {
            try {
    
                // Find the item on the cart with the matching productName
    
                // Increment or decrement the quantity of the matching product until the current
                // quantity is reached (Note: Keep a look out when then input quantity is 0,
                // here we need to remove the item completely from the cart)
    
                // Find web element corresponding to each of the cart items
                WebElement cartParent = driver.findElement(By.className("cart"));
                List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));
    
                int currentQty;
                for (WebElement item : cartContents) {
                    // Find the matching product from the cart items
                    if (productName.contains(item.findElement(By.xpath("//*[@class='MuiBox-root css-1gjj37g']/div[1]")).getText())) {
                        currentQty = Integer.valueOf(item.findElement(By.className("css-olyig7")).getText());
    
                        // Click on the + or - buttons appropriately to set the correct quantity of the
                        // product
                        while (currentQty != quantity) {
                            if (currentQty < quantity) {
                                // increase Qty
                                item.findElements(By.tagName("button")).get(1).click();
                             
                            } else {
                                // decrease Qty
                                item.findElements(By.tagName("button")).get(0).click();
                            }
    
                            synchronized (driver){
                                driver.wait(2000);
                            }
    
                            currentQty = Integer
                                    .valueOf(item.findElement(By.xpath("//div[@data-testid=\"item-qty\"]")).getText());
                        }
    
                        return true;
                    }
                }
    
                return false;
            } catch (Exception e) {
                if (quantity == 0)
                    return true;
                System.out.println(("exception occurred when updating cart"));
                return false;
            }
        }

        public Boolean verifyCartContents(List<String> expectedCartContents) {
            try {
                // Get all the cart items as an array of webelements
    
                // Iterate through expectedCartContents and check if item with matching product
                // name is present in the cart
                
                // WebElement cartParent = driver.findElement(By.xpath("//div[@class='cart MuiBox-root css-0']"));
                // List<WebElement> cartContents = cartParent.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']"));
                // Thread.sleep(5000);
                // ArrayList<String> actualCartContents = new ArrayList<String>() {
                // };
                // for (WebElement cartItem : cartContents) {
                //     actualCartContents.add(cartItem.findElement(By.xpath("//div[@class='MuiBox-root css-1gjj37g']")).getText().split("\n")[0]);
                // }
    
                // for (String expected : expectedCartContents) {
                //     // To trim as getText() trims cart item title
                //     if (!actualCartContents.contains(expected.trim())) {
                //         return false;
                //     }
                // }
                List<WebElement> cartElem = driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']"));
            List<String> cartItems = new ArrayList<>();
            for(WebElement e: cartElem)
            {
                Thread.sleep(1000);
                cartItems.add(e.getText().trim());
            }

            System.out.println(cartItems);


            if(cartItems.equals(expectedCartContents))
            {
                System.out.println(cartItems);
                return false;
            }
            // Get all the cart items as an array of webelements

            // Iterate through expectedCartContents and check if item with matching product
            // name is present in the cart
            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }}

    }



